package org.jseats.unit;

import org.jseats.model.Tally;

/**
 * Created by alvaro on 24/01/15.
 */
public class TallyBuilder {
	public static TallyBuilder aNew() {
		return new TallyBuilder();
	}

	public Tally build() {
		return new Tally();
	}
}
