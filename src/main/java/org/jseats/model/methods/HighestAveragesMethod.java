package org.jseats.model.methods;

import java.util.Properties;

import org.jseats.model.SeatAllocationException;
import org.jseats.model.result.Result;
import org.jseats.model.result.Result.ResultType;
import org.jseats.model.tally.InmutableTally;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HighestAveragesMethod extends SeatAllocationMethod {

	static Logger log = LoggerFactory.getLogger(HighestAveragesMethod.class);

	public abstract double nextDivisor(int round);

	@Override
	public Result process(InmutableTally tally, Properties properties)
			throws SeatAllocationException {
		
		int numberOfCandidates = tally.getNumberOfCandidates();
		int numberOfSeats = Integer.parseInt(properties
				.getProperty("numberOfSeats"),numberOfCandidates);
		double firstDivisor = Double.parseDouble(properties.getProperty(
				"firstDivisor", "-1"));
		boolean modifiedFirstDivisor = (firstDivisor == -1) ? false : true;
		boolean groupSeatsPerCandidate = Boolean.parseBoolean(properties
				.getProperty("groupSeatsPerCandidate", "false"));


		int numberOfUnallocatedSeats = numberOfSeats;

		int[] seatsPerCandidate = new int[numberOfCandidates];
		int[][] averagesPerRound = new int[numberOfCandidates][numberOfSeats];

		log.debug("numberOfSeats: " + numberOfSeats);
		log.debug("groupSeatsPerCandidate: " + groupSeatsPerCandidate);

		// Create the averages table
		for (int round = 0; round < numberOfSeats; round++) {

			double divisor;
			if (modifiedFirstDivisor) {
				// Then user has provided an alternative first divisor
				divisor = firstDivisor;
				modifiedFirstDivisor = false;
				nextDivisor(round); //Ignore first methods' first divisor
			} else
				divisor = nextDivisor(round);

			log.debug("Current divisor: " + divisor);

			// Let's divide every candidate's votes with the current round's
			// divisor.
			for (int candidate = 0; candidate < numberOfCandidates; candidate++) {
				averagesPerRound[candidate][round] = (int) (tally
						.getCandidateAt(candidate).getVotes() / divisor);
			}
		}

		Result result = new Result(ResultType.MULTIPLE);

		// Find max votes of the average table and add a seat to the appropriate
		// candidate.
		while (numberOfUnallocatedSeats > 0) {

			int maxCandidate = -1;
			int maxRound = -1;
			int maxVotes = -1;

			for (int round = 0; round < numberOfCandidates; round++) {
				for (int candidate = 0; candidate < numberOfCandidates; candidate++) {

					if (averagesPerRound[candidate][round] == maxVotes) {
						// TODO detect tie. Warning, this might be an
						// intermediate tie.
					}

					if (averagesPerRound[candidate][round] > maxVotes) {
						maxCandidate = candidate;
						maxRound = round;
						maxVotes = averagesPerRound[candidate][round];
					}
				}
			}

			if (groupSeatsPerCandidate)
				seatsPerCandidate[maxCandidate]++;
			else
				result.addSeat(tally.getCandidateAt(maxCandidate));

			// Eliminate this maximum coordinates and iterate
			averagesPerRound[maxCandidate][maxRound] = -2;
			numberOfUnallocatedSeats--;
		}

		if (groupSeatsPerCandidate) {
			// Time to spread allocated seats to results

			for (int candidate = 0; candidate < numberOfCandidates; candidate++) {
				for (int seat = 0; seat < seatsPerCandidate[candidate]; seat++) {
					result.addSeat(tally.getCandidateAt(candidate));
				}
			}
		}

		debugResult(result);

		return result;
	}

	private void debugResult(Result result) {
		if (log.isDebugEnabled()) {
			for (int i = 0; i < result.getNumerOfSeats(); i++) {
				log.debug("seat #" + i + ": " + result.getSeatAt(i));
			}
		}
	}
}
