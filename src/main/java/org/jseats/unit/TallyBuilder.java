package org.jseats.unit;

import org.jseats.model.Candidate;
import org.jseats.model.Tally;

/**
 * Created by alvaro on 24/01/15.
 */
public class TallyBuilder {
	private Candidate candidate;

	public static TallyBuilder aNew() {
		return new TallyBuilder();
	}

	public Tally build() {
		final Tally tally = new Tally();
		if(null != candidate){
			tally.addCandidate(candidate);
		}
		return tally;

	}

	public TallyBuilder with(Candidate candidate) {
		this.candidate = candidate;
		return this;
	}
}
