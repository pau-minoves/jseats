package org.jseats.model.methods;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.jseats.model.Candidate;
import org.jseats.model.InmutableTally;
import org.jseats.model.Result;
import org.jseats.model.Result.ResultType;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.SeatAllocationMethod;
import org.jseats.model.tie.TieBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EqualProportionsMethod implements SeatAllocationMethod {

	Logger log = LoggerFactory.getLogger(EqualProportionsMethod.class);

	NumberFormat df = DecimalFormat.getInstance();

	protected double coefficient(int seatNumber) {

		return 1 / Math.sqrt(seatNumber * (seatNumber + 1));
	}

	public EqualProportionsMethod() {
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(2);
		df.setRoundingMode(RoundingMode.DOWN);
	}

	@Override
	public Result process(InmutableTally tally, Properties properties,
			TieBreaker tieBreaker) throws SeatAllocationException {

		// Get properties

		int numberOfCandidates = tally.getNumberOfCandidates();
		int numberOfSeats = Integer.parseInt(properties.getProperty(
				"numberOfSeats", Integer.toString(numberOfCandidates)));
		int numberOfInitialSeats = Integer.parseInt(properties.getProperty(
				"numberOfInitialSeats", Integer.toString(0)));

		// Allocating initial seats

		log.debug("Allocating " + numberOfInitialSeats
				+ " initial seats per candidate.");

		int[] candidateSeats = new int[numberOfCandidates];
		Result result = new Result(ResultType.MULTIPLE);

		for (int candidate = 0; candidate < numberOfCandidates; candidate++) {
			for (int seat = 0; seat < numberOfInitialSeats; seat++) {
				result.addSeat(tally.getCandidateAt(candidate));
				candidateSeats[candidate]++;
				numberOfSeats--;
			}
		}

		log.debug("Number of seats remaining: " + numberOfSeats);

		// Order by priority

		double[] candidatePriority = new double[numberOfCandidates];

		while (numberOfSeats > 0) {

			log.debug("Seat #" + numberOfSeats + ": Computing candidate");

			// Get priority for each candidate
			for (int candidate = 0; candidate < numberOfCandidates; candidate++) {

				double coefficient = coefficient(candidateSeats[candidate]);
				int votes = tally.getCandidateAt(candidate).getVotes();

				candidatePriority[candidate] = votes * coefficient;

				if (log.isDebugEnabled())
					log.debug("Coefficient: " + df.format(coefficient)
							+ " Priority: "
							+ df.format(candidatePriority[candidate]) + " "
							+ tally.getCandidateAt(candidate));
			}

			// Find candidate with higher priority:

			int maxCandidate = -1;
			double maxPriority = -1;

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

			// and assign the seat

			log.debug("Seat #" + numberOfSeats + ": Winner candidate is "
					+ tally.getCandidateAt(maxCandidate));

			result.addSeat(tally.getCandidateAt(maxCandidate));
			candidateSeats[maxCandidate]++;

			numberOfSeats--;
		}

		// Summary
		if (log.isTraceEnabled())
			for (int candidate = 0; candidate < numberOfCandidates; candidate++) {
				log.trace("Candidate " + tally.getCandidateAt(candidate)
						+ " has " + candidateSeats[candidate] + " seats.");
			}

		return result;
	}
}
