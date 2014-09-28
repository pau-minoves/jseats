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

		result.addCandidate(new Candidate("A"));
		result.addCandidate(new Candidate("B"));
		result.addCandidate(new Candidate("C"));

		assertTrue(result.containsCandidate(new Candidate("B")));
		assertFalse(result.containsCandidate(new Candidate("D")));
	}

	@Test(expected = SeatAllocationException.class)
	public void checkResultTypeOnGetCandidate() throws SeatAllocationException {
		Result result = new Result(ResultType.TIE);

		result.addCandidate(new Candidate("A"));
		result.addCandidate(new Candidate("B"));

		result.getCandidate();
	}
	
	@Test(expected = SeatAllocationException.class)
	public void checkResultTypeOnSetCandidate() throws SeatAllocationException {
		Result result = new Result(ResultType.TIE);

		result.addCandidate(new Candidate("A"));
		result.addCandidate(new Candidate("B"));

		result.setCandidate(new Candidate("A"));
	}
}
