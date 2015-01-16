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
		
		assertEquals(0, tally.getEffectiveVotes());
		
		tally.addCandidate(new Candidate("A",100));

		assertEquals(100, tally.getEffectiveVotes());

		tally.addCandidate(new Candidate("B",50));
		
		assertEquals(150, tally.getEffectiveVotes());
	}
	
	@Test
	@Ignore // TODO to fix this, probably hashCode needs to be properly implemented.
	public void effectiveVotesOverridenCandidate() {
		Tally tally = new Tally();
		
		assertEquals(0, tally.getEffectiveVotes());
		
		tally.addCandidate(new Candidate("A",100));

		assertEquals(100, tally.getEffectiveVotes());

		tally.addCandidate(new Candidate("A",50));
		
		assertEquals(50, tally.getEffectiveVotes());
	}

}
