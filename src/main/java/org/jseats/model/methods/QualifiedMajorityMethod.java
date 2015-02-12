package org.jseats.model.methods;

import java.util.Properties;

import org.jseats.model.ImmutableTally;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.Result.ResultType;
import org.jseats.model.tie.TieBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QualifiedMajorityMethod extends SimpleMajorityMethod {

	static Logger log = LoggerFactory.getLogger(QualifiedMajorityMethod.class);

	private int minimumVotes;
	private double qualifiedProportion;

	@Override
	public Result process(ImmutableTally tally, Properties properties, TieBreaker tieBreaker)
			throws SeatAllocationException {

		if (properties.containsKey("minimumVotes")) {
			minimumVotes = Integer.parseInt(properties
					.getProperty("minimumVotes"));
			log.debug("Using provided minimum votes: " + minimumVotes);
		} else {
			// let's calculate minimum votes from qualified proportion
			qualifiedProportion = Double.parseDouble(properties
					.getProperty("qualifiedProportion"));

			minimumVotes = (int) Math.round(tally.getPotentialVotes()
					* qualifiedProportion);
			log.debug("Using calculated minimum votes: " + minimumVotes
					+ " over " + tally.getPotentialVotes() + " potential votes");
		}

		if (minimumVotes > tally.getEffectiveVotes()) {
			log.debug("Not enougth votes casted (" + tally.getEffectiveVotes()
					+ ") to reach a qualified majority (" + minimumVotes + ").");

			return new Result(ResultType.UNDECIDED);
		}

		Result result = super.process(tally, properties, tieBreaker);

		// Either SINGLE or TIE, minimumVotes are not reached.
		if (result.getSeats().get(0).getVotes() < minimumVotes)
			return new Result(ResultType.UNDECIDED);

		return result;
	}
}
