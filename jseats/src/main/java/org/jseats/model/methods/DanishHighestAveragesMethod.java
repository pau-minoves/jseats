package org.jseats.model.methods;

public class DanishHighestAveragesMethod extends HighestAveragesMethod {

	private int currentDivisor = -2;

	@Override
	public double nextDivisor(int round) {

		// Danish divisor (1, 4, 7, 10, ...)
		return currentDivisor += 3;
	}

}
