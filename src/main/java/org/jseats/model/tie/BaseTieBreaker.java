package org.jseats.model.tie;

import java.util.ArrayList;
import java.util.List;

import org.jseats.model.Candidate;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.Result.ResultType;

/**
 * This helper base class clones the passed List of candidates so order is not
 * changed from the provided List. Please take into account that the pointed
 * Candidate instances are not cloned. TieBreaker implementations cloud, but
 * should not, modify them.
 */
public abstract class BaseTieBreaker implements TieBreaker {

	@Override
	public Candidate breakTie(List<Candidate> candidates) {
		return innerBreakTie(new ArrayList<Candidate>(candidates));
	}

	@Override
	public Candidate breakTie(Candidate... candidates) {

		List<Candidate> innerCandidates = new ArrayList<Candidate>(candidates.length);

		for (Candidate candidate : candidates)
			innerCandidates.add(candidate);

		return innerBreakTie(innerCandidates);
	}

	@Override
	public Result breakTie(Result tieResult) throws SeatAllocationException {

		Candidate winnerCandidate = innerBreakTie(new ArrayList<Candidate>(tieResult.getSeats()));
		if (winnerCandidate == null) {
			return tieResult;
		} else {
			Result result = new Result(ResultType.SINGLE);
			result.addSeat(winnerCandidate);
			return result;
		}
	}

	/**
	 * Override this method to provide a custom TieBreaker implementation. The
	 * provided List object is local so can be modified without side-effects on
	 * calling code. The provided Candidate instances are not local and should
	 * not be modified unless that is part of your TieBreaker contract.
	 * 
	 * @param candidates
	 * @return the candidate that wins the tie or null of none.
	 */
	public abstract Candidate innerBreakTie(List<Candidate> candidates);

}
