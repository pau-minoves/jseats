package org.jseats.unit;

import static org.junit.Assert.*;

import org.jseats.model.Candidate;
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
}
