package org.jseats.model;

import java.util.Properties;

import org.jseats.model.tie.TieBreaker;

public interface SeatAllocationMethod {

	public abstract Result process(InmutableTally tally,
			Properties properties, TieBreaker tieBreaker)
			throws SeatAllocationException;
}
