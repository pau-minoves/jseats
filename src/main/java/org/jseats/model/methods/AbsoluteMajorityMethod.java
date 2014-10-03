package org.jseats.model.methods;

import java.util.Properties;

import org.jseats.model.SeatAllocationException;
import org.jseats.model.result.Result;
import org.jseats.model.tally.InmutableTally;

public class AbsoluteMajorityMethod extends QualifiedMajorityMethod {

	@Override
	public Result process(InmutableTally tally, Properties properties) throws SeatAllocationException {
		
		// TODO this requires more testing for rounding errors.
		int minimumVotes = (tally.getPotentialVotes()/2)+1;
		
		properties.setProperty("minimumVotes", Integer.toString(minimumVotes));
		return super.process(tally, properties);
	}
}
