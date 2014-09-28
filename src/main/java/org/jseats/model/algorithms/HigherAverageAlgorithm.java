package org.jseats.model.algorithms;

import java.util.Arrays;
import java.util.Properties;

import org.jseats.model.InmutableTally;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationAlgorithm;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.Result.ResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HigherAverageAlgorithm extends SeatAllocationAlgorithm {

	static Logger log = LoggerFactory
			.getLogger(HigherAverageAlgorithm.class);
	
	@Override
	public Result process(InmutableTally tally, Properties properties)
			throws SeatAllocationException {
		
		int numberOfCandidates = tally.getNumerOfCandidates();
		int numberOfSeats = Integer.parseInt(properties.getProperty("numberOfSeats"));
		int numberOfUnallocatedSeats = numberOfSeats;
		
		int[] seatsPerQuotient = new int[tally.getNumerOfCandidates()];
		int[] votesPerRemainder = new int[tally.getNumerOfCandidates()];
		int[] seatsTotal = new int[tally.getNumerOfCandidates()];
		
		// decimals dropped
		int quotient = (int) quotient(tally.getEffectiveVotes(),numberOfSeats);
		
		log.debug("quotient is: " + quotient);
		
		// Let's assign direct seats to parties
		for(int i = 0; i < numberOfCandidates; i++) {
			seatsPerQuotient[i] = tally.getCandidateAt(i).getVotes() / quotient;
			votesPerRemainder[i] = tally.getCandidateAt(i).getVotes() - (seatsPerQuotient[i] * quotient);
			numberOfUnallocatedSeats -= seatsPerQuotient[i];
		}
		
		for(int i = 0; i < numberOfCandidates; i++) {
			log.debug("seatsPerQuotient["+i+"]: " + seatsPerQuotient[i]);
			log.debug("votesPerRemainder["+i+"]: " + votesPerRemainder[i]);
		}
		
		log.debug("numberOfUnallocatedSeats: "+numberOfUnallocatedSeats);
		
		// Let's assign unallocated seats to parties below the quotient, 
		// from more voted to less voted until no more unallocated seats remain.
		Arrays.sort(votesPerRemainder);
		
		Result result = new Result(ResultType.MULTIPLE);
			
		return result;
	}

	public abstract double quotient(int numberOfVotes, int numberOfSeats);
}
