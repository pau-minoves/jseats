package org.jseats.model.methods;

import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.jseats.model.Candidate;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.result.Result;
import org.jseats.model.result.Result.ResultType;
import org.jseats.model.tally.InmutableTally;

public abstract class RankMethod extends SeatAllocationMethod {

	@Override
	public Result process(InmutableTally tally, Properties properties)
			throws SeatAllocationException {

		int numberOfCandidates = tally.getNumberOfCandidates();
		int numberOfSeats = Integer.parseInt(
				properties.getProperty("numberOfSeats"), numberOfCandidates);

		int[] candidatePriority = new int[numberOfCandidates];

		List<Candidate> candidates = new ArrayList<Candidate>();

		// Get priority
		for (int i = 0; i < numberOfCandidates; i++)
			candidatePriority[i] = (int) (tally.getCandidateAt(i).getVotes() * multiplier(
					tally.getCandidateAt(i).getVotes(), i, numberOfCandidates));

		Result result = new Result(ResultType.MULTIPLE);

		// Order by priority.
		while (numberOfSeats > 0) {

			int maxCandidate = -1;
			int maxVotes = -1;

			for (int candidate = 0; candidate < numberOfCandidates; candidate++) {

				if (candidatePriority[candidate] == maxVotes) {
					// TODO detect tie. Warning, this might be an
					// intermediate tie.
				}

				if (candidatePriority[candidate] > maxVotes) {
					maxCandidate = candidate;
					maxVotes = candidatePriority[candidate];
				}
			}

			result.addSeat(tally.getCandidateAt(maxCandidate));

			// Eliminate this maximum coordinates and iterate
			candidatePriority[maxCandidate] = -2;
			numberOfSeats--;
		}

		return null;
	}

	protected abstract double multiplier(int votes, int seatNumber,
			int numberOfCandidates);
}
