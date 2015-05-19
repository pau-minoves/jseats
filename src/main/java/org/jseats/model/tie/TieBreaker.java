package org.jseats.model.tie;

import java.util.List;

import org.jseats.model.Candidate;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;

/**
 * A TieBreaker will take a list of candidates and order them by a criteria
 * other than votes.
 *
 */
public interface TieBreaker {

	/**
	 * Get the name of this tie breaker for the purpose of resolving and
	 * logging.
	 * 
	 * @return tie breaker name
	 */
	public String getName();

	/**
	 * Re-order list of candidates.
	 * 
	 * @param candidates
	 *            (This object not modified).
	 * @return The top candidate where with more priority according to tie
	 *         breaker implementation criteria. If unsolvable, null is returned.
	 */
	@Deprecated
	public Candidate breakTie(List<Candidate> candidates);

	/**
	 * Same as <code>breakTie(List&lt;Candidate&gt; candidates)</code> but using
	 * varargs.
	 * 
	 * @param candidate
	 *            a variable lists of Candidate parameters.
	 * @return The top candidate where with more priority according to tie
	 *         breaker implementation criteria. If unsolvable, null is returned.
	 */
	@Deprecated
	public Candidate breakTie(Candidate... candidate);

	/**
	 * This method will take a TIE result, inspect the candidates and return a
	 * SINGLE result with the TIE solved.
	 * 
	 * @param tieResult
	 *            A Result object containing only the candidates on a tie.
	 *            ResultType of this Result object must be TIE.
	 * @return A result of type SINGLE with only the winning candidate.
	 * 
	 * @throws SeatAllocationException
	 */
	public Result breakTie(Result tieResult) throws SeatAllocationException;
}
