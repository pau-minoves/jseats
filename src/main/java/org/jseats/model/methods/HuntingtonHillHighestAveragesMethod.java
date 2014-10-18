package org.jseats.model.methods;

@Deprecated
public class HuntingtonHillHighestAveragesMethod extends HighestAveragesMethod {

	@Override
	public double nextDivisor(int round) {

		// Huntington-Hill divisor (1, 2, 3, 4, ...)
		return Math.sqrt(round * (round + 1));
	}

}
