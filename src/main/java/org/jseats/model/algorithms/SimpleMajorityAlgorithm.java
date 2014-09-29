package org.jseats.model.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.jseats.model.Candidate;
import org.jseats.model.InmutableTally;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.Result.ResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleMajorityAlgorithm extends SeatAllocationAlgorithm {

	static Logger log = LoggerFactory.getLogger(SimpleMajorityAlgorithm.class);

	@Override
	public Result process(InmutableTally tally, Properties properties)
			throws SeatAllocationException {

		List<Candidate> candidates = new ArrayList<Candidate>();

		int maxVotes = 0;

		for (int i = 0; i < tally.getNumberOfCandidates(); i++) {

			log.trace("iterating candidate: " + tally.getCandidateAt(i));
			log.trace("Current max votes: " + maxVotes);
			
			if (tally.getCandidateAt(i).getVotes() == maxVotes)
				candidates.add(tally.getCandidateAt(i));
			else if (tally.getCandidateAt(i).getVotes() > maxVotes) {
				candidates.clear();
				maxVotes = tally.getCandidateAt(i).getVotes();
				candidates.add(tally.getCandidateAt(i));
			}
		}

		for (Candidate candidate : candidates)
			log.debug("Final candidate: " + candidate);
		
		Result result;

		if (candidates.size() == 1)
			result = new Result(ResultType.SINGLE);
		else
			result = new Result(ResultType.TIE);

		result.setCandidates(candidates);

		log.debug("Processing ended with " + result.getType() + " result at "
				+ maxVotes + " votes");

		return result;
	}
}
