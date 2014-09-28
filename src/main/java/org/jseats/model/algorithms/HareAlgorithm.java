package org.jseats.model.algorithms;

public class HareAlgorithm extends HigherAverageAlgorithm {

	@Override
	public double quotient(int numberOfVotes, int numberOfSeats) {
		
		// Hare quotient formula
		return numberOfVotes / numberOfSeats;
	}
}
