package org.jseats.model.algorithms;

public class DroopLargestRemainderAlgorithm extends LargestRemainderAlgorithm{

	@Override
	public double quotient(int numberOfVotes, int numberOfSeats) {
		
		// Droop quotient formula
		return 1 + ( numberOfVotes / (numberOfSeats + 1) );
	}

}
