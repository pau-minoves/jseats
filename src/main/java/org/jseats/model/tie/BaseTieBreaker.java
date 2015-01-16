package org.jseats.model.tie;

import java.util.ArrayList;
import java.util.List;

import org.jseats.model.Candidate;

public abstract class BaseTieBreaker implements TieBreaker {

	@Override
	public Candidate breakTie(List<Candidate> candidates) {
		return innerBreakTie(new ArrayList<Candidate>(candidates));
	}
	
	@Override
	public Candidate breakTie(Candidate... candidates) {
		
		List<Candidate> innerCandidates = new ArrayList<Candidate>(
				candidates.length);
		
		for (Candidate candidate : candidates)
			innerCandidates.add(candidate);

		return innerBreakTie(innerCandidates);
	}

	public abstract Candidate innerBreakTie(List<Candidate> candidates);

}
