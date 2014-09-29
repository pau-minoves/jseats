package org.jseats.model.algorithms;

public class HareLargestRemainderAlgorithm extends LargestRemainderAlgorithm {

	@Override
	public double quotient(int numberOfVotes, int numberOfSeats) {
		
		// Hare quotient formula
		return numberOfVotes / numberOfSeats;
	}
}
