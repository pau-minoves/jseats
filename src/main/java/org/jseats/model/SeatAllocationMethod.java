package org.jseats.model;

import java.util.Properties;

import org.jseats.model.tie.TieBreaker;

public interface SeatAllocationMethod {

	public abstract Result process(ImmutableTally tally,
			Properties properties, TieBreaker tieBreaker)
			throws SeatAllocationException;
}
