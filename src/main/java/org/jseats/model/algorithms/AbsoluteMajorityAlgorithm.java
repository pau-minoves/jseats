package org.jseats.model.algorithms;

import java.util.Properties;

import org.jseats.model.InmutableTally;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;

public class AbsoluteMajorityAlgorithm extends QualifiedMajorityAlgorithm {

	@Override
	public Result process(InmutableTally tally, Properties properties) throws SeatAllocationException {
		
		// TODO this requires more testing for rounding errors.
		int minimumVotes = (tally.getPotentialVotes()/2)+1;
		
		properties.setProperty("minimumVotes", Integer.toString(minimumVotes));
		return super.process(tally, properties);
	}
}
