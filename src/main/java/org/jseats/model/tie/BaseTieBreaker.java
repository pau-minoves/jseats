package org.jseats.model.tie;

import java.util.ArrayList;
import java.util.List;

import org.jseats.model.Candidate;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.Result.ResultType;

public abstract class BaseTieBreaker implements TieBreaker {

	@Override
	@Deprecated
	public Candidate breakTie(List<Candidate> candidates) {
		return innerBreakTie(new ArrayList<Candidate>(candidates));
	}

	@Override
	@Deprecated
	public Candidate breakTie(Candidate... candidates) {

		List<Candidate> innerCandidates = new ArrayList<Candidate>(
				candidates.length);

		for (Candidate candidate : candidates)
			innerCandidates.add(candidate);

		return innerBreakTie(innerCandidates);
	}

	@Override
	public Result breakTie(Result tieResult) throws SeatAllocationException {

		Candidate winnerCandidate = innerBreakTie(new ArrayList<Candidate>(
				tieResult.getSeats()));
		if (winnerCandidate == null) {
			return tieResult;
		} else {
			Result result = new Result(ResultType.SINGLE);
			result.addSeat(winnerCandidate);
			return result;
		}
	}

	public abstract Candidate innerBreakTie(List<Candidate> candidates);

}
