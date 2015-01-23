package org.jseats.model.methods;

import com.google.common.collect.Collections2;
import org.jseats.model.Candidate;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.Tally;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class RankMethodShould {

	static Logger log = LoggerFactory.getLogger(RankMethodShould.class);

	@Test
	//Rank should not mind the order in the tallysheet
	public void not_mind_the_order() throws Exception {


		ByVotesRankMethod sut = new ByVotesRankMethod();

		List<Candidate> listOfCandidates = new ArrayList<>();
		listOfCandidates.add(new Candidate("A", 1));
		listOfCandidates.add(new Candidate("B", 1));
		listOfCandidates.add(new Candidate("candidateC", 2));
		final Properties properties = createProperties();
		Tally tally = new Tally();
		for (Candidate current : listOfCandidates) {
			tally.addCandidate(current);

		}

		final Result result = sut.process(tally, properties, null);

		assertEquals(Result.ResultType.MULTIPLE, result.getType());
		assertEquals("numberOfSeats", 1, result.getNumerOfSeats());
		assertEquals(new Candidate("candidateC"), result.getSeatAt(0));
	}

	@Test
	public void property_testing_fixed_amounts_different_order() throws Exception {

		Random r = new Random(1L);
		ByVotesRankMethod sut = new ByVotesRankMethod();

		List<Candidate> listOfCandidates = new ArrayList<>();
		listOfCandidates.add(new Candidate("A", 1));
		listOfCandidates.add(new Candidate("B", 1));
		listOfCandidates.add(new Candidate("D", 2));
		listOfCandidates.add(new Candidate("E", 2));
		listOfCandidates.add(new Candidate("F", 3));
		listOfCandidates.add(new Candidate("F1", 3));
		listOfCandidates.add(new Candidate("G", 4));
		listOfCandidates.add(new Candidate("candidateC", 5));
		final Properties properties = createProperties();

		Collection<List<Candidate>> permutations = Collections2.permutations(listOfCandidates);

		for (List<Candidate> current : permutations) {
			final Tally tally = new Tally();

			for (Candidate candidate : current) {
				tally.addCandidate(candidate);
			}

			final Result result = sut.process(tally, properties, null);
			if (result.getType() != Result.ResultType.MULTIPLE) {
				printResult(result);
			}
			assertEquals(Result.ResultType.MULTIPLE, result.getType());
			assertEquals("numberOfSeats", 1, result.getNumerOfSeats());
			assertEquals(new Candidate("candidateC"), result.getSeatAt(0));
		}
	}

	@Test
	public void property_testing_random_amounts_different_order() throws Exception {

		Random r = new Random(1L);
		ByVotesRankMethod sut = new ByVotesRankMethod();

		for (int i = 0; i < 50_000; i++) {
			List<Candidate> listOfCandidates = new ArrayList<>();
			listOfCandidates.add(new Candidate("A", getSmallishVotes(r)));
			listOfCandidates.add(new Candidate("B", getSmallishVotes(r)));
			listOfCandidates.add(new Candidate("D", getSmallishVotes(r)));
			listOfCandidates.add(new Candidate("E", getSmallishVotes(r)));
			listOfCandidates.add(new Candidate("F", getSmallishVotes(r)));
			listOfCandidates.add(new Candidate("F1", getSmallishVotes(r)));
			listOfCandidates.add(new Candidate("G", getSmallishVotes(r)));
			listOfCandidates.add(new Candidate("candidateC", getSmallishVotes(r) + 50));
			final Properties properties = createProperties();

			Collection<List<Candidate>> permutations = Collections2.permutations(listOfCandidates);

			for (List<Candidate> current : permutations) {
				final Tally tally = new Tally();


				for (Candidate candidate : current) {
					tally.addCandidate(candidate);
				}

				final Result result = sut.process(tally, properties, null);
				if (result.getType() != Result.ResultType.MULTIPLE) {
					printResult(result);
				}
				assertEquals(Result.ResultType.MULTIPLE, result.getType());
				assertEquals("numberOfSeats", 1, result.getNumerOfSeats());
				assertEquals(new Candidate("candidateC"), result.getSeatAt(0));
			}
		}

	}

	public void printResult(Result result) {

		log.debug("type: " + result.getType());
		log.debug("number of seats: " + result.getNumerOfSeats());

		for (int i = 0; i < result.getSeats().size(); i++) {
			log.debug("seat #" + i + ": " + result.getSeatAt(i));
		}
	}

	private int getSmallishVotes(Random r) {
		return Math.abs(r.nextInt(50));
	}

	private Properties createProperties() {
		final Properties properties = new Properties();
		properties.setProperty("numberOfSeats","1");
		properties.setProperty("groupSeatsPerCandidate", "true");
		return properties;
	}

	@Test
	//Rank candidates by votes produces a TIE
	public void tie() throws SeatAllocationException {

		ByVotesRankMethod sut = new ByVotesRankMethod();

		final Tally tally = new Tally();
		final Candidate candidateA = new Candidate("A", 150);
		tally.addCandidate(candidateA);
		final Candidate candidateB = new Candidate("B", 150);
		tally.addCandidate(candidateB);
		tally.addCandidate(new Candidate("C", 75));
		tally.addCandidate(new Candidate("D", 200));
		final Properties properties = new Properties();
		properties.setProperty("numberOfSeats","4");
		properties.setProperty("groupSeatsPerCandidate", "true");
		final Result result = sut.process(tally, properties, null);

		assertEquals(Result.ResultType.TIE, result.getType());
		assertEquals("numberOfSeats", 2, result.getNumerOfSeats());
		assertEquals(candidateA, result.getSeatAt(0));
		assertEquals(candidateB, result.getSeatAt(1));
	}
}
