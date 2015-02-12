package org.jseats.model.methods;

import java.util.Properties;

import org.jseats.model.ImmutableTally;
import org.jseats.model.Result;
import org.jseats.model.Result.ResultType;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.SeatAllocationMethod;
import org.jseats.model.tie.TieBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

		Result result;
		if (numberOfSeats == 1)
			result = new Result(ResultType.SINGLE);
		else
			result = new Result(ResultType.MULTIPLE);
		Result tieResult = new Result(ResultType.TIE);

		// Order by priority.
		while (numberOfSeats > 0) {

			int maxCandidate = -1;
			int maxPriority = -1;
			boolean isMaxCandidateATie = false;

			// find max candidate
			for (int candidate = 0; candidate < numberOfCandidates; candidate++) {

				if (candidatePriority[candidate] > maxPriority) {

					isMaxCandidateATie = false;
					tieResult.empty();

					maxCandidate = candidate;
					maxPriority = candidatePriority[candidate];

				} else if (candidatePriority[candidate] == maxPriority) {
					isMaxCandidateATie = true;
					if (!tieResult.containsSeatForCandidate(tally
							.getCandidateAt(maxCandidate)))
						tieResult.addSeat(tally.getCandidateAt(maxCandidate));
					tieResult.addSeat(tally.getCandidateAt(candidate));
				}
			}

			// if there is a tie on max candidate, try to solve it with tie
			// breaker.
			if (isMaxCandidateATie) {

				log.debug("Tie between  " + tieResult.toString());

				if (tieBreaker == null)
					return tieResult;

				log.debug("Using tie breaker: " + tieBreaker.getName());

				Result solvedTie = tieBreaker.breakTie(tieResult);

				log.debug("solvedTie = " + solvedTie.toString());

				if (solvedTie.getType() == ResultType.TIE)
					return /* non- */solvedTie;
				else
					maxCandidate = tally.getCandidateIndex(solvedTie
							.getSeatAt(0));
			}

			log.debug("Adding candidate " + tally.getCandidateAt(maxCandidate)
					+ " to result.");
			result.addSeat(tally.getCandidateAt(maxCandidate));

			// Eliminate this maximum coordinate and iterate
			candidatePriority[maxCandidate] = -2;
			numberOfSeats--;
		}

		return result;
	}

	protected abstract double multiplier(int votes, int seatNumber,
			int numberOfCandidates);
}
