package org.jseats.model.tally;

import org.jseats.model.Tally;
import org.jseats.model.TallyFilter;

public class NullTallyFilter implements TallyFilter {

	@Override
	public String getName() {
		return "null-filter";
	}

	@Override
	public Tally filter(Tally tally) {
		return tally;
	}

}
