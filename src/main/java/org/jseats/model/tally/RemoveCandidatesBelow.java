package org.jseats.model.tally;

import java.util.Iterator;

import org.jseats.model.Candidate;
import org.jseats.model.Tally;
import org.jseats.model.TallyFilter;

public class RemoveCandidatesBelow implements TallyFilter {

	@Override
	public String getName() {
		return "remove-candidates-below-filter";
	}

	private int minimumVotes;

	public RemoveCandidatesBelow(int votes) {
		this.minimumVotes = votes;
	}

	@Override
	public Tally filter(Tally tally) {

		Iterator<Candidate> i = tally.getCandidates().iterator();

		while (i.hasNext())
			if (i.next().getVotes() < minimumVotes)
				i.remove();

		return tally;
	}
}
