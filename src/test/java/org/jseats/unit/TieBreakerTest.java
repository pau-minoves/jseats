package org.jseats.unit;

import static org.junit.Assert.assertEquals;

import org.codehaus.plexus.util.StringInputStream;
import org.jseats.SeatAllocatorProcessor;
import org.jseats.model.Candidate;
import org.jseats.model.Result;
import org.jseats.model.Result.ResultType;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.Tally;
import org.jseats.model.tie.InteractiveTieBreaker;
import org.jseats.model.tie.RandomTieBreaker;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("deprecation")
public class TieBreakerTest {

	Logger log = LoggerFactory.getLogger(TieBreakerTest.class);

	@Test
	public void interactiveTieBreaker() throws SeatAllocationException {

		Candidate A = new Candidate("A", 200);
		Candidate B = new Candidate("B", 200);

		Tally tally = new Tally();
		tally.addCandidate(A);
		tally.addCandidate(B);

		SeatAllocatorProcessor processor = new SeatAllocatorProcessor();

		processor.setTally(tally);
		processor.setMethodByName("SimpleMajority");

		// We setup a StringInputStream preselecting option 1.
		processor.setTieBreaker(new InteractiveTieBreaker(
				new StringInputStream("1"), log));

		Result result = processor.process();

		assertEquals(ResultType.SINGLE, result.getType());
		assertEquals(B, result.getSeatAt(0));

		// We setup a StringInputStream preselecting option 0.
		processor.setTieBreaker(new InteractiveTieBreaker(
				new StringInputStream("0"), log));

		result = processor.process();

		assertEquals(ResultType.SINGLE, result.getType());
		assertEquals(A, result.getSeatAt(0));

		// We setup a StringInputStream with an initial wrong input, then 0.
		processor.setTieBreaker(new InteractiveTieBreaker(
				new StringInputStream("a\n3\n0"), log));

		result = processor.process();

		assertEquals(ResultType.SINGLE, result.getType());
		assertEquals(A, result.getSeatAt(0));

		// We setup a StringInputStream preselecting none.
		processor.setTieBreaker(new InteractiveTieBreaker(
				new StringInputStream("-1"), log));

		result = processor.process();

		assertEquals(ResultType.TIE, result.getType());
	}

	@Test
	public void randomTieBreaker() throws SeatAllocationException {

		Candidate A = new Candidate("A", 200);
		Candidate B = new Candidate("B", 200);

		Tally tally = new Tally();
		tally.addCandidate(A);
		tally.addCandidate(B);

		SeatAllocatorProcessor processor = new SeatAllocatorProcessor();

		processor.setTally(tally);
		processor.setMethodByName("SimpleMajority");

		processor.setTieBreaker(new RandomTieBreaker());

		Result result = processor.process();

		// We can't test randomness here but we can do a minimal test.
		assertEquals(ResultType.SINGLE, result.getType());

	}
}
