package org.jseats.model.methods;

public class ByVotesRankMethod extends RankMethod {

	@Override
	protected double multiplier(int votes, int seatNumber,
			int numberOfCandidates) {
		
		return 1; // 1 votes equals 1 priority point
	}

}
