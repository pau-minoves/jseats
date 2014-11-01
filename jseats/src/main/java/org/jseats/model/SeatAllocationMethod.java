package org.jseats.model;

import java.util.Properties;

public interface SeatAllocationMethod {

	public abstract Result process(InmutableTally tally, Properties properties)
			throws SeatAllocationException;
}
