package org.jseats.model.methods;

public class ImperialiLargestRemainderMethod extends
		LargestRemainderMethod {

	@Override
	public double quotient(int numberOfVotes, int numberOfSeats) {
		
		// Imperiali quotient formula
		return numberOfVotes / (numberOfSeats + 2);
	}

}
