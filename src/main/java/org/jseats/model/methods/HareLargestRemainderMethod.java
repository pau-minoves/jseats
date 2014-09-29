package org.jseats.model.methods;

public class HareLargestRemainderMethod extends LargestRemainderMethod {

	@Override
	public double quotient(int numberOfVotes, int numberOfSeats) {
		
		// Hare quotient formula
		return numberOfVotes / numberOfSeats;
	}
}
