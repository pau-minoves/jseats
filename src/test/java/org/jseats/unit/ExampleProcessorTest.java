package org.jseats.unit;

import static org.junit.Assert.*;

import org.jseats.SeatAllocatorProcessor;
import org.jseats.model.Candidate;
import org.jseats.model.Result.ResultType;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.Tally;
import org.junit.Test;
import org.jseats.model.Result;

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

		assertEquals(tally.getNumberOfCandidates(), 3);
		assertEquals(tally.getEffectiveVotes(), 600);
		
		// Configure a seat allocation algorithm
		processor.setProperty("minimumVotes", "150");
		processor.setAlgorithmByName("QualifiedMajority");
		
		// Get the tally in
		processor.setTally(tally);
		
		// Shake
		Result result = processor.process();

		assertEquals(result.getType(),ResultType.SINGLE);
		assertEquals(result.getNumerOfSeats(),1);
		assertEquals(result.getSeatAt(0).getName(), "White Party");
		
		// Let's try again
		tally.addCandidate(new Candidate("Black Party",300));
		result = processor.process();
		
		assertEquals(result.getType(),ResultType.TIE);
		assertEquals(result.getNumerOfSeats(),2);
		assertEquals(result.getSeatAt(0).getName(), "White Party");
		assertEquals(result.getSeatAt(1).getName(), "Black Party");
		
		// Let's try again
		processor.setProperty("minimumVotes", "350");
		result = processor.process();

		assertEquals(result.getType(),ResultType.UNDECIDED);
		assertEquals(result.getNumerOfSeats(),0);
		
		// You can find more complex examples in /src/test/resources/stories
	}

}
