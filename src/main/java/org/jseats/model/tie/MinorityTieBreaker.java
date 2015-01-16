package org.jseats.model.tie;

import java.util.Iterator;
import java.util.List;

import org.jseats.model.Candidate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinorityTieBreaker extends BaseTieBreaker {

	@Override
	public String getName() {
		return "minority-tie-breaker";
	}

	static Logger log = LoggerFactory.getLogger(MinorityTieBreaker.class);

	@Override
	public Candidate innerBreakTie(List<Candidate> candidates) {

		log.debug("Called Minority Tie Breaker with " + candidates.size()
				+ " candidates.");

		log.trace("Candidates:");

		Iterator<Candidate> i = candidates.iterator();

		while (i.hasNext()) {
			Candidate candidate = i.next();

			log.trace(candidate.toString());

			if (!candidate.propertyIs("minority", "yes"))
				i.remove();
		}

		log.debug(candidates.size() + " candidates left after filtering.");

		if (candidates.size() == 1) {
			log.debug("Top candidate: " + candidates.get(0));
			return candidates.get(0);
		} else
			return null;
	}

}
