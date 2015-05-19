package org.jseats.model.methods;

/**
 * Order candidates by votes.
 */
public class ByVotesRankMethod extends RankMethod {

	@Override
	protected double multiplier(int votes, int seatNumber,
			int numberOfCandidates) {

		return 1; // 1 vote equals 1 priority point
	}

}
