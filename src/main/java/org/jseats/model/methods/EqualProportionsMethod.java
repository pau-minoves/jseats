package org.jseats.model.methods;

public class EqualProportionsMethod extends RankMethod {

	@Override
	protected double multiplier(int votes, int seatNumber,
			int numberOfCandidates) {

		return  1 / Math.sqrt(seatNumber*(seatNumber-1));
	}

}
