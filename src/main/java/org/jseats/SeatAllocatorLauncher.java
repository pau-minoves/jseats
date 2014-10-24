package org.jseats;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.jseats.model.Candidate;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.Tally;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

public class SeatAllocatorLauncher {

	@Parameter(names = { "-h", "--help" }, description = "Print this message.", help = true)
	private boolean help;

	@Parameter(names = { "-v", "--verbose" }, description = "Level of verbosity.")
	private boolean verbose = false;

	@Parameter(names = { "-lF", "--list-filters" }, description = "List built-in tally filters.")
	private boolean listFilters = false;

	@Parameter(names = { "-lD", "--list-decorators" }, description = "List built-in tally filters.")
	private boolean listDecorators = false;

	@Parameter(names = { "-lm", "--list-methods" }, description = "List built-in seat allocation methods.")
	private boolean listMethods = false;

	@Parameter(names = { "-c", "--candidate" }, description = "List candidates to add to tally. Candidates follow the format Name:Votes.")
	private List<String> candidates;

	@Parameter(names = { "-pv", "--potential-votes" }, description = "Potential votes. If not set, defaults to effective votes (sum of all casted votes).")
	private int potentialVotes = -1;

	@Parameter(names = { "-D", "--processor-property" }, description = "Processor properties as in -DnumberOfSeats=105")
	private List<String> properties;

	@Parameter(names = { "-M", "--method" }, description = "Seat allocation method to use. See --list-methods")
	private String method;

	@Parameter(names = { "-ic", "--input-config" }, description = "Configuration input file.")
	private String inputConfig;

	@Parameter(names = { "-it", "--input-tally" }, description = "Tally input file. Overrides tally provided in configuration via --input-config, if any.")
	private String inputTally;

	@Parameter(names = { "-oc", "--output-config" }, description = "Configuration output file.")
	private String outputConfig;

	@Parameter(names = { "-o", "--output-result" }, description = "Result output file.")
	private String outputResult;

	Logger log = (Logger) LoggerFactory.getLogger(SeatAllocatorLauncher.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			mainWithThrow(args);

		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}

	public static void mainWithThrow(String[] args)
			throws SeatAllocationException {

		SeatAllocatorLauncher launcher = new SeatAllocatorLauncher();
		JCommander commander = new JCommander(launcher);
		commander.setProgramName("JSeats");

		try {
			commander.parse(args);

			if (launcher.help)
				commander.usage();
			else
				launcher.launch();

		} catch (ParameterException pe) {
			commander.usage();
			throw pe;

		} catch (Exception e) {
			if (launcher.verbose)
				e.printStackTrace(System.err);
			throw e;
		}
	}

	private void launch() throws SeatAllocationException {

		if (verbose)
			replaceRootLogger(Level.DEBUG);
		else
			replaceRootLogger(Level.INFO);

		if (listFilters || listDecorators || listMethods)
			throw new SeatAllocationException("Not implemented");

		Tally tally;
		SeatAllocatorProcessor processor;

		if (inputConfig != null) {
			try {
				processor = new SeatAllocatorProcessor(
						ProcessorConfig
								.fromXML(new FileInputStream(inputConfig)));
			} catch (FileNotFoundException | JAXBException e) {
				throw new SeatAllocationException(e.getMessage(), e);
			}
		} else
			processor = new SeatAllocatorProcessor();

		if (inputTally != null) {
			try {
				tally = Tally.fromXML(new FileInputStream(inputTally));
			} catch (FileNotFoundException | JAXBException e) {
				throw new SeatAllocationException(e.getMessage(), e);
			}
		} else
			tally = new Tally();

		if (candidates != null)
			for (String candidate : candidates)
				tally.addCandidate(Candidate.fromString(candidate));

		if (potentialVotes != -1)
			tally.setPotentialVotes(potentialVotes);

		processor.setTally(tally);

		if (properties != null)
			for (String property : properties) {

				int i = property.indexOf("=");

				switch (i) {
				case 0:
					throw new SeatAllocationException(
							"Badly formatted processor property: " + property);
				case -1:
					processor.setProperty(property, "true");
					break;
				default:
					processor.setProperty(property.substring(0, i),
							property.substring(i + 1));
				}
			}

		if (outputConfig != null) {
			try {
				processor.getConfig().toXML(new FileOutputStream(outputConfig));
			} catch (FileNotFoundException | JAXBException e) {
				throw new SeatAllocationException(e.getMessage(), e);
			}
		}

		if (method == null)
			throw new SeatAllocationException("Method must be provided.");

		processor.setMethodByName(method);

		Result result = processor.process();

		log.info("Type: " + result.getType());
		log.info("Number of seats: " + result.getNumerOfSeats());

		for (int i = 0; i < result.getNumerOfSeats(); i++) {
			log.info("Seat " + i + "\t" + result.getSeatAt(i).getName() + "\t"
					+ result.getSeatAt(i).getVotes());
		}

		if (outputResult != null) {
			try {
				result.toXML(new FileOutputStream(outputResult));
			} catch (FileNotFoundException | JAXBException e) {
				throw new SeatAllocationException(e.getMessage(), e);
			}
		}

	}

	private void replaceRootLogger(Level level) {
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

		ConsoleAppender<ILoggingEvent> cappender = new ConsoleAppender<ILoggingEvent>();

		PatternLayoutEncoder encoder = new PatternLayoutEncoder();
		encoder.setContext(root.getLoggerContext());
		encoder.setPattern("%msg%n");
		encoder.start();

		cappender.setContext(root.getLoggerContext());
		cappender.setEncoder(encoder);
		cappender.start();

		root.detachAppender("STDOUT");
		root.addAppender(cappender);
		root.setLevel(level);
	}
}
