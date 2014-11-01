package org.jseats.model.methods;

public class ImperialiHighestAveragesMethod extends HighestAveragesMethod {

	private int currentDivisor = 2;
	
	@Override
	public double nextDivisor(int round) {
		
		// Imperiali divisor (2, 3, 4, ...)
		return currentDivisor++;
	}

}
