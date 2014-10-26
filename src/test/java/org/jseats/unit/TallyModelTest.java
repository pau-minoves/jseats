package org.jseats.unit;

import static org.junit.Assert.*;

import org.jseats.model.Candidate;
import org.jseats.model.Tally;
import org.junit.Ignore;
import org.junit.Test;

public class TallyModelTest {

	@Test
	public void effectiveVotes() {
		Tally tally = new Tally();
		
		assertEquals(tally.getEffectiveVotes(), 0);
		
		tally.addCandidate(new Candidate("A",100));

		assertEquals(tally.getEffectiveVotes(), 100);

		tally.addCandidate(new Candidate("B",50));
		
		assertEquals(tally.getEffectiveVotes(), 150);
	}
	
	@Test
	@Ignore // TODO to fix this, probably hashCode needs to be properly implemented.
	public void effectiveVotesOverridenCandidate() {
		Tally tally = new Tally();
		
		assertEquals(tally.getEffectiveVotes(), 0);
		
		tally.addCandidate(new Candidate("A",100));

		assertEquals(tally.getEffectiveVotes(), 100);

		tally.addCandidate(new Candidate("A",50));
		
		assertEquals(tally.getEffectiveVotes(), 50);
	}

}
