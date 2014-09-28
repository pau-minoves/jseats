package org.jseats.model.algorithms;

import java.util.Properties;

import org.jseats.model.InmutableTally;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.Result.ResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QualifiedMajorityAlgorithm extends SeatAllocationAlgorithm {

	static Logger log = LoggerFactory
			.getLogger(QualifiedMajorityAlgorithm.class);

	private int minimumVotes;
	private double qualifiedProportion;

	@Override
	public Result process(InmutableTally tally, Properties properties)
			throws SeatAllocationException {

		log.debug("properties: " + properties.toString());

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
			log.debug("Using calculated minimum votes: " + minimumVotes + " over " + tally.getPotentialVotes() + " potential votes");
		}

		log.debug("Using minimum votes: " + minimumVotes);

		if (minimumVotes > tally.getEffectiveVotes()) {
			log.debug("Not enougth votes casted (" + tally.getEffectiveVotes()
					+ ") to reach a qualified majority (" + minimumVotes + ").");

			return new Result(ResultType.UNDECIDED);
		}

		SeatAllocationAlgorithm simpleMajority = getByName("SimpleMajority");

		Result result = simpleMajority.process(tally, properties);

		// Either SINGLE or TIE, minimumVotes are not reached.
		if(result.getCandidates().get(0).getVotes() < minimumVotes)
			return new Result(ResultType.UNDECIDED);
		
		return result;
	}
}
