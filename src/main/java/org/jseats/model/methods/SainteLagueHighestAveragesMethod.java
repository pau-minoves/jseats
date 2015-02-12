package org.jseats.model.methods;

public class SainteLagueHighestAveragesMethod extends HighestAveragesMethod {

	private int currentDivisor = -1;
	
	@Override
	public double nextDivisor(int round) {
		
		// Sainte-Lague (aka Webster) divisor (odd positive natural: 1, 3, 5, 7, ...)
		return currentDivisor+=2;
	}

}
