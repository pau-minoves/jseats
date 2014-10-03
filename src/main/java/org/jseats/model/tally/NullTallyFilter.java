package org.jseats.model.tally;

public class NullTallyFilter implements TallyFilter {

	@Override
	public Tally filter(Tally tally) {
		return tally;
	}

}
