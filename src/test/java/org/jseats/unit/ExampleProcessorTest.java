package org.jseats.unit;

import static org.junit.Assert.*;

import org.jseats.SeatAllocatorProcessor;
import org.jseats.model.Candidate;
import org.jseats.model.Result;
import org.jseats.model.Tally;
import org.jseats.model.Result.ResultType;
import org.jseats.model.SeatAllocationException;
import org.junit.Test;

public class ExampleProcessorTest {

	@Test
	public void exampleProcessor() throws SeatAllocationException {
		
		// Create a processor
		SeatAllocatorProcessor processor = new SeatAllocatorProcessor();
		
		// Get a tally
		Tally tally = new Tally();

		// Put some data in
		// (Alternatively, you could use Tally.fromXML() to load from disk
		tally.addCandidate(new Candidate("Green Party",100));
		tally.addCandidate(new Candidate("White Party",300));
		tally.addCandidate(new Candidate("Red Party",200));
		tally.setPotentialVotes(1000);

		assertEquals(3, tally.getNumberOfCandidates());
		assertEquals(600, tally.getEffectiveVotes());
		
		// Configure a seat allocation method
		processor.setProperty("minimumVotes", "150");
		processor.setMethodByName("QualifiedMajority");
		
		// Get the tally in
		processor.setTally(tally);
		
		// Shake
		Result result = processor.process();

		assertEquals(ResultType.SINGLE, result.getType());
		assertEquals(1, result.getNumberOfSeats());
		assertEquals("White Party", result.getSeatAt(0).getName());
		
		// Let's try again
		tally.addCandidate(new Candidate("Black Party",300));
		result = processor.process();
		
		assertEquals(ResultType.TIE, result.getType());
		assertEquals(2, result.getNumberOfSeats());
		assertEquals("White Party", result.getSeatAt(0).getName());
		assertEquals("Black Party", result.getSeatAt(1).getName());
		
		// Let's try again
		processor.setProperty("minimumVotes", "350");
		result = processor.process();

		assertEquals(result.getType(),ResultType.UNDECIDED);
		assertEquals(result.getNumberOfSeats(),0);
		
		// You can find more complex examples in /src/test/resources/stories
	}

}
