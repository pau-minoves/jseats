package org.jseats.model.tie;

import java.util.List;
import java.util.Random;

import org.jseats.model.Candidate;

public class RandomTieBreaker extends BaseTieBreaker {

	Random rand = new Random();

	@Override
	public String getName() {
		return "random-tie-breaker";
	}

	@Override
	public Candidate innerBreakTie(List<Candidate> candidates) {
		return candidates.get(rand.nextInt(candidates.size()));
	}

}
