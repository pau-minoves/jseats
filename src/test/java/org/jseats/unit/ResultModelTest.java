package org.jseats.unit;

import static org.junit.Assert.*;

import org.jseats.model.Candidate;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.Result.ResultType;
import org.junit.Test;

public class ResultModelTest {

	@Test
	public void containsCandidate() {
		Result result = new Result(ResultType.SINGLE);

		result.addSeat(new Candidate("A"));
		result.addSeat(new Candidate("B"));
		result.addSeat(new Candidate("C"));

		assertTrue(result.containsSeatForCandidate(new Candidate("B")));
		assertFalse(result.containsSeatForCandidate(new Candidate("D")));
	}
}
