package org.jseats.model.methods;

public class DHondtHighestAveragesMethod extends HighestAveragesMethod {

	private int currentDivisor = 1;
	
	@Override
	public double nextDivisor(int round) {
		
		// D'Hondt divisor (1, 2, 3, 4, ...)
		return currentDivisor++;
	}

}
