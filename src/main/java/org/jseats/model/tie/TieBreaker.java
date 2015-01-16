package org.jseats.model.tie;

import java.util.List;

import org.jseats.model.Candidate;

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
	public Candidate breakTie(List<Candidate> candidates);
	
	/**
	 * Same as breakTie(List<Candidate> candidates) but using varargs.
	 * 
	 * @param candidate
	 *            a variable lists of Candidate parameters.
	 * @return The top candidate where with more priority according to tie
	 *         breaker implementation criteria. If unsolvable, null is returned.
	 */
	public Candidate breakTie(Candidate... candidate);
}
