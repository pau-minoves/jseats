package org.jseats.unit;

import static org.junit.Assert.*;

import org.jseats.model.Candidate;
import org.jseats.model.SeatAllocationException;
import org.junit.Test;

public class CandidateModelTest {

	@Test
	public void hasVotes() {

		Candidate candidate = new Candidate("A");

		assertFalse(candidate.hasVotes());

		candidate.setVotes(100);

		assertTrue(candidate.hasVotes());

		candidate.hasVotes(false);

		assertFalse(candidate.hasVotes());
	}

	@Test
	public void hasVotesFromConstructor() {

		Candidate candidate = new Candidate("A", 100);

		assertTrue(candidate.hasVotes());

		candidate.hasVotes(false);

		assertFalse(candidate.hasVotes());
	}

	@Test
	public void setsProperties() throws SeatAllocationException {

		Candidate candidate = new Candidate("A", 100);
		
		candidate.setProperty("gender", "male");
		
		assertEquals("male", candidate.getProperty("gender"));

		candidate = Candidate.fromString("B:200:gender=woman");
		assertEquals("woman", candidate.getProperty("gender"));

		candidate = Candidate.fromString("B:200:gender=");
		assertEquals(null, candidate.getProperty("gender"));

		candidate = Candidate.fromString("B:200:gender=alien:minority=yes");
		assertEquals("alien", candidate.getProperty("gender"));
		assertEquals("yes", candidate.getProperty("minority"));

		candidate = new Candidate("C", 300);
		candidate.setProperty("minority", "no");
		assertEquals("C:300:minority=no", candidate.toString());
		candidate.setProperty("gender", "man");
		assertEquals("C:300:gender=man:minority=no", candidate.toString());

		candidate.setProperty("minority", null);
		assertEquals("C:300:gender=man", candidate.toString());
		assertTrue(candidate.propertyIs("gender", "man"));
	}
}
