package org.jseats.model.methods;

import org.jseats.model.Candidate;
import org.jseats.model.ImmutableTally;
import org.jseats.model.Result;
import org.jseats.model.Result.ResultType;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.SeatAllocationMethod;
import org.jseats.model.tie.TieBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public abstract class RankMethod implements SeatAllocationMethod {

	static Logger log = LoggerFactory.getLogger(RankMethod.class);

	@Override
	public Result process(ImmutableTally tally, Properties properties,
			TieBreaker tieBreaker) throws SeatAllocationException {

		int numberOfCandidates = tally.getNumberOfCandidates();
		int numberOfSeats = Integer.parseInt(properties.getProperty(
				"numberOfSeats", Integer.toString(numberOfCandidates)));

		int[] candidatePriority = new int[numberOfCandidates];

		// Get priority
		for (int i = 0; i < numberOfCandidates; i++) {
			candidatePriority[i] = (int) (tally.getCandidateAt(i).getVotes() * multiplier(
					tally.getCandidateAt(i).getVotes(), i, numberOfCandidates));

			log.debug(tally.getCandidateAt(i) + " with "
					+ tally.getCandidateAt(i).getVotes() + " gets priority "
					+ candidatePriority[i]);
		}

		Result result = new Result(ResultType.MULTIPLE);

		// Order by priority.
		while (numberOfSeats > 0) {

			int maxCandidate = -1;
			int maxPriority = -1;
			boolean isMaximumATie = false;

			for (int candidate = 0; candidate < numberOfCandidates; candidate++) {

				if (candidatePriority[candidate] == maxPriority) {

					isMaximumATie = true;
				} else if (candidatePriority[candidate] > maxPriority) {
					isMaximumATie = false;
					maxCandidate = candidate;
					maxPriority = candidatePriority[candidate];
				}
			}

			if (!isMaximumATie) {

				log.debug("Adding candidate " + tally.getCandidateAt(maxCandidate)
						+ " to result.");
				result.addSeat(tally.getCandidateAt(maxCandidate));

			} else {
				break;
			}

			// Eliminate this maximum coordinate and iterate
			candidatePriority[maxCandidate] = -2;
			numberOfSeats--;
		}

		// Order by priority.
		while (numberOfSeats > 0) {

			int maxCandidate = -1;
			int maxPriority = -1;
			boolean foundTie = false;

		for (int candidate = 0; candidate < numberOfCandidates; candidate++) {

			if (candidatePriority[candidate] == maxPriority) {
					log.debug("Tie between  "
							+ tally.getCandidateAt(maxCandidate) + " and "
							+ tally.getCandidateAt(candidate));

					if (tieBreaker != null) {

						log.debug("Using tie breaker: " + tieBreaker.getName());

						Candidate topCandidate = tieBreaker.breakTie(
								tally.getCandidateAt(candidate),
								tally.getCandidateAt(maxCandidate));

						if (topCandidate == null) {
							Result tieResult = new Result(ResultType.TIE);
							tieResult.addSeat(tally
									.getCandidateAt(maxCandidate));
							tieResult.addSeat(tally.getCandidateAt(candidate));

							return tieResult;
						} else {
							maxCandidate = tally
									.getCandidateIndex(topCandidate);
							maxPriority = candidatePriority[maxCandidate];
						}

					} else {
						Result tieResult = new Result(ResultType.TIE);
						tieResult.addSeat(tally.getCandidateAt(maxCandidate));
						tieResult.addSeat(tally.getCandidateAt(candidate));

						return tieResult;
					}
			} else if (candidatePriority[candidate] > maxPriority) {
				maxCandidate = candidate;
				maxPriority = candidatePriority[candidate];
			}
		}

		if(!foundTie) {

			log.debug("Adding candidate " + tally.getCandidateAt(maxCandidate)
					+ " to result.");
			result.addSeat(tally.getCandidateAt(maxCandidate));

		}

		// Eliminate this maximum coordinate and iterate
		candidatePriority[maxCandidate] = -2;
		numberOfSeats--;
	}


		return result;
	}

	protected abstract double multiplier(int votes, int seatNumber,
			int numberOfCandidates);
}
