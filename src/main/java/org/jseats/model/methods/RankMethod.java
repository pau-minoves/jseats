package org.jseats.model.methods;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.jseats.model.Candidate;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.result.Result;
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

		// Order by priority.

		return null;
	}

	protected abstract double multiplier(int votes, int seatNumber,
			int numberOfCandidates);
}
