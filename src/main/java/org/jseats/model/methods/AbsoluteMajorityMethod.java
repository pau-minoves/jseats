package org.jseats.model.methods;

import java.util.Properties;

import javax.xml.bind.annotation.XmlRootElement;

import org.jseats.model.ImmutableTally;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.tie.TieBreaker;

@XmlRootElement
public class AbsoluteMajorityMethod extends QualifiedMajorityMethod {

	@Override
	public Result process(ImmutableTally tally, Properties properties, TieBreaker tieBreaker)
			throws SeatAllocationException {

		// TODO this requires more testing for rounding errors.
		int minimumVotes = (tally.getPotentialVotes() / 2) + 1;

		properties.setProperty("minimumVotes", Integer.toString(minimumVotes));
		return super.process(tally, properties, tieBreaker);
	}
}
