package org.jseats.model.tie;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.jseats.model.Candidate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinorityTieBreaker implements TieBreaker {

	static Logger log = LoggerFactory.getLogger(MinorityTieBreaker.class);

	@Override
	public List<Candidate> breakTie(List<Candidate> candidates) {

		log.debug("Called Minority Tie Breaker with " + candidates.size()
				+ " candidates.");

		log.trace("Initial order:");
		for (Candidate candidate : candidates)
			log.trace(candidate.toString());

		Collections.sort(candidates, new Comparator<Candidate>() {
			@Override
			public int compare(Candidate left, Candidate right) {

				boolean leftIs = left.getProperty("minority")!= null && left.getProperty("minority").contentEquals(
						"yes");

				boolean rightIs = right.getProperty("minority") != null
						&& right.getProperty("minority").contentEquals(
						"yes");

				if (leftIs == rightIs)
					return 0; // equals

				if (leftIs)
					return -1; // left is more
				else
					return 1; // right is more
			}

		});

		log.trace("Final order:");
		for (Candidate candidate : candidates)
			log.trace(candidate.toString());

		return candidates;
	}

}
