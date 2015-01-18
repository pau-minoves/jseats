package org.jseats.unit;

import static org.junit.Assert.assertEquals;

import org.jseats.model.Candidate;
import org.jseats.model.Tally;
import org.junit.Ignore;
import org.junit.Test;

public class TallyModelTest {

	@Test
	public void effectiveVotes() {
		Tally tally = new Tally();

		tally.setPotentialVotes(200);

		assertEquals(0, tally.getEffectiveVotes());

		tally.addCandidate(new Candidate("A", 100));

		assertEquals(100, tally.getEffectiveVotes());

		tally.addCandidate(new Candidate("B", 50));

		assertEquals(150, tally.getEffectiveVotes());

		assertEquals("tally (150/200) with 2 candidates: A:100, B:50.",
				tally.toString());
	}

	@Test
	@Ignore
	// TODO to fix this, probably hashCode needs to be properly implemented.
	public void effectiveVotesOverridenCandidate() {
		Tally tally = new Tally();

		assertEquals(0, tally.getEffectiveVotes());

		tally.addCandidate(new Candidate("A", 100));

		assertEquals(100, tally.getEffectiveVotes());

		tally.addCandidate(new Candidate("A", 50));

		assertEquals(50, tally.getEffectiveVotes());

	}
}
