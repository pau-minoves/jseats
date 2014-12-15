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
	 * Re-order list of candidates.
	 * 
	 * @param candidates
	 * @return a list of candidates where .get(0) is the candidate with more
	 *         priority according to tie breaker implementation criteria.
	 */
	public List<Candidate> breakTie(List<Candidate> candidates);
}
