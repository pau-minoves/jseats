package org.jseats.unit;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Properties;

import javax.xml.bind.JAXBException;

import org.jseats.ProcessorConfig;
import org.jseats.SeatAllocatorDefaultResolver;
import org.jseats.model.Candidate;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.Tally;
import org.jseats.model.result.AppendTextToCandidateNameDecorator;
import org.jseats.model.tally.NullTallyFilter;
import org.junit.Test;

public class SerializationTest {

	@Test
	public void tallySerializesSimple() throws SeatAllocationException,
			JAXBException {

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		Candidate a = Candidate.fromString("A:100");
		Candidate b = Candidate.fromString("B:20:minority=yes");

		Tally tally = new Tally();

		tally.addCandidate(a);
		tally.addCandidate(b);
		tally.setPotentialVotes(200);

		tally.toXML(out);

		System.out.println(out.toString());

		Tally newTally = Tally.fromXML(new ByteArrayInputStream(out
				.toByteArray()));

		assertEquals(a, newTally.getCandidateAt(0));
		assertEquals(b, newTally.getCandidateAt(1));
		assertEquals("yes", newTally.getCandidateAt(1).getProperty("minority"));
	}

	@Test
	public void tallySerializesWithNullValue() throws SeatAllocationException,
			JAXBException {

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		Candidate a = Candidate.fromString("A:100");
		Candidate b = new Candidate("B:20");
		b.setProperty("minority", null); // no effect

		Tally tally = new Tally();

		tally.addCandidate(a);
		tally.addCandidate(b);
		tally.setPotentialVotes(200);

		tally.toXML(out);

		System.out.println(out.toString());

		Tally newTally = Tally.fromXML(new ByteArrayInputStream(out
				.toByteArray()));

		assertEquals(a, newTally.getCandidateAt(0));
		assertEquals(b, newTally.getCandidateAt(1));
		assertEquals(null, newTally.getCandidateAt(1).getProperty("minority"));
	}

	@Test
	public void processorConfigSerializes() throws JAXBException,
			SeatAllocationException {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ProcessorConfig config = new ProcessorConfig();

		config.setProperty("numberOfSeats", String.valueOf(60));
		config.setMethodName("SimpleMajority");
		Properties props = new Properties();
		props.setProperty("text", "-candidate");
		config.addResultDecorator(new AppendTextToCandidateNameDecorator(props));
		config.addTallyFilter(new NullTallyFilter());

		config.toXML(out);

		System.out.println(out.toString());

		ProcessorConfig newConfig = ProcessorConfig
				.fromXML((new ByteArrayInputStream(out.toByteArray())));

		// this is needed to resolve filter and decorator names to actual
		// objects.
		newConfig.resolveReferences(new SeatAllocatorDefaultResolver());

		assertEquals("60", newConfig.getProperty("numberOfSeats"));
		assertTrue(newConfig.getResultDecorator().get(0) instanceof AppendTextToCandidateNameDecorator);
		assertTrue(newConfig.getTallyFilters().get(0) instanceof NullTallyFilter);

	}
}
