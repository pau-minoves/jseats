package org.jseats.unit;

import org.jseats.model.Candidate;
import org.jseats.model.Tally;

import java.util.Arrays;

/**
 * Created by alvaro on 24/01/15.
 */
public class TallyBuilder {
	private Candidate[] candidates;

	public static TallyBuilder aNew() {
		return new TallyBuilder();
	}

	public Tally build() {
		final Tally tally = new Tally();
		if(null != candidates){
			Arrays.asList(candidates).forEach(tally::addCandidate);
		}
		return tally;

	}

	public TallyBuilder with(Candidate... candidates) {
		this.candidates = candidates;
		return this;
	}
}
