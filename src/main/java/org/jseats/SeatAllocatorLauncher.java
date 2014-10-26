package org.jseats;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.jseats.model.Candidate;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.Tally;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

public class SeatAllocatorLauncher {

	@Parameter(names = { "-h", "--help" }, description = "Print this message.", help = true)
	private boolean help;

	@Parameter(names = { "-v", "--verbose" }, description = "Increase level of verbosity.")
	private boolean verbose;

	@Parameter(names = { "-lf", "--list-filters" }, description = "List built-in tally filters.")
	private boolean listFilters;

	@Parameter(names = { "-ld", "--list-decorators" }, description = "List built-in result decorators.")
	private boolean listDecorators;

	@Parameter(names = { "-lm", "--list-methods" }, description = "List built-in seat allocation methods.")
	private boolean listMethods;

	@Parameter(names = { "-c", "--candidate" }, description = "Add candidate to tally. Candidates follow the format Name:Votes.")
	private List<String> candidates;

	@Parameter(names = { "-pv", "--potential-votes" }, description = "Potential votes. If not set, defaults to effective votes (sum of all casted votes).")
	private int potentialVotes = -1;

	@Parameter(names = { "-d", "--processor-property" }, description = "Processor properties as in -D numberOfSeats=105.")
	private List<String> properties;

	@Parameter(names = { "-m", "--method" }, description = "Seat allocation method to use. See --list-methods for available methods.")
	private String method;

	@Parameter(names = { "-ic", "--input-config" }, description = "Configuration input file.")
	private String inputConfig;

	@Parameter(names = { "-it", "--input-tally" }, description = "Tally input file. Overrides tally provided in configuration via --input-config, if any.")
	private String inputTally;

	@Parameter(names = { "-oc", "--output-config" }, description = "Configuration output file.")
	private String outputConfig;

	@Parameter(names = { "-ot", "--output-tally" }, description = "Tally output file.")
	private String outputTally;

	@Parameter(names = { "-o", "--output-result" }, description = "Result output file.")
	private String outputResult;

	Logger log = LoggerFactory.getLogger(SeatAllocatorLauncher.class);

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

	public static void mainWithThrow(String[] args) throws Exception {

		SeatAllocatorLauncher launcher = new SeatAllocatorLauncher();
		JCommander commander = new JCommander(launcher);
		commander.setProgramName("JSeats");
		commander.setCaseSensitiveOptions(false);
		commander.setAllowAbbreviatedOptions(true);

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

	private void launch() throws Exception {

		if (verbose)
			setLoggerLevel(Level.DEBUG);
		else
			setLoggerLevel(Level.INFO);

		SeatAllocatorResolver resolver = new SeatAllocatorDefaultResolver();

		if (listFilters || listDecorators || listMethods) {

			if (listFilters)
				for (String filter : resolver.listTallyFilters())
					log.info(filter);

			if (listDecorators)
				for (String filter : resolver.listResultDecorators())
					log.info(filter);

			if (listMethods)
				for (String filter : resolver.listMethods())
					log.info(filter);

			return;
		}

		Tally tally;
		SeatAllocatorProcessor processor;

		if (inputConfig != null) {
			processor = new SeatAllocatorProcessor(
					ProcessorConfig.fromXML(new FileInputStream(inputConfig)));

			processor.getConfig().resolveReferences(processor.getResolver());
		} else
			processor = new SeatAllocatorProcessor();

		if (processor.config.getMethod() == null && method == null)
			throw new SeatAllocationException(
					"Method must be provided by either --method or --input-config.");

		if (method != null) {
			processor.setMethodByName(method);
		}

		if (inputTally != null) {
			tally = Tally.fromXML(new FileInputStream(inputTally));

		} else
			tally = (processor.getTally() != null) ? processor.getTally()
					: new Tally();

		if (candidates != null)
			for (String candidate : candidates)
				tally.addCandidate(Candidate.fromString(candidate));

		if (potentialVotes != -1)
			tally.setPotentialVotes(potentialVotes);

		processor.setTally(tally);

		if (outputTally != null)
			processor.getTally().toXML(new FileOutputStream(outputTally));

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

		if (outputConfig != null)
			processor.getConfig().toXML(new FileOutputStream(outputConfig));

		Result result = processor.process();

		log.info("Type: " + result.getType());
		log.info("Number of seats: " + result.getNumerOfSeats());

		for (int i = 0; i < result.getNumerOfSeats(); i++) {
			log.info("Seat " + i + "\t" + result.getSeatAt(i).getName() + "\t"
					+ result.getSeatAt(i).getVotes());
		}

		if (outputResult != null)
			result.toXML(new FileOutputStream(outputResult));
	}

	private void setLoggerLevel(Level level) {

		// ROOT is the common root id for logback and slf4j
		org.slf4j.Logger rootLogger = org.slf4j.LoggerFactory.getLogger("ROOT");

		// If underlying logger is logback-classic (default), we know how to
		// change the layout. Otherwise silently do nothing.
		if (rootLogger instanceof ch.qos.logback.classic.Logger) {

			ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) rootLogger;

			PatternLayoutEncoder encoder = new PatternLayoutEncoder();
			encoder.setContext(root.getLoggerContext());
			encoder.setPattern("%msg%n");
			encoder.start();

			((ConsoleAppender<ILoggingEvent>) root.getAppender("STDOUT"))
					.setEncoder(encoder);
			((ConsoleAppender<ILoggingEvent>) root.getAppender("STDOUT"))
					.start();
			root.setLevel(level);
		}
	}
}
