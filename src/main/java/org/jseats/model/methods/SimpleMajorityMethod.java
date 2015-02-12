package org.jseats.model.methods;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.jseats.model.Candidate;
import org.jseats.model.ImmutableTally;
import org.jseats.model.Result;
import org.jseats.model.Result.ResultType;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.SeatAllocationMethod;
import org.jseats.model.tie.TieBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleMajorityMethod implements SeatAllocationMethod {

	static Logger log = LoggerFactory.getLogger(SimpleMajorityMethod.class);

	@Override
	public Result process(ImmutableTally tally, Properties properties,
			TieBreaker tieBreaker) throws SeatAllocationException {

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
		else {
			if (tieBreaker != null) {

				log.debug("Using tie breaker: " + tieBreaker.getName());

				Candidate topCandidate = tieBreaker.breakTie(candidates);

				log.debug("top candidate: " + topCandidate);

				if (topCandidate != null) {

					candidates.clear();
					candidates.add(topCandidate);
					result = new Result(ResultType.SINGLE);
				} else
					result = new Result(ResultType.TIE);
			} else
				result = new Result(ResultType.TIE);
		}

		result.setSeats(candidates);

		log.debug("Processing ended with " + result.getType() + " result at "
				+ maxVotes + " votes");

		return result;
	}
}
