package org.jseats.model.methods;

import com.google.common.collect.Collections2;
import org.jseats.model.Candidate;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.Tally;
import org.jseats.unit.TallyBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class RankMethodShould {

	static Logger log = LoggerFactory.getLogger(RankMethodShould.class);

	@Test
	public void not_mind_the_order_in_the_tallysheet() throws Exception {


		ByVotesRankMethod sut = new ByVotesRankMethod();
		final Properties properties = createProperties();

		Tally tally = TallyBuilder.aNew().with(new Candidate("A", 1),new Candidate("B", 1),new Candidate("candidateC", 2)).build();

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
//		listOfCandidates.add(new Candidate("D", 2));
//		listOfCandidates.add(new Candidate("E", 2));
//		listOfCandidates.add(new Candidate("F", 3));
//		listOfCandidates.add(new Candidate("F1", 3));
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
	//TODO AGB increase i to 500 or so
	public void property_testing_random_amounts_different_order() throws Exception {

		Random r = new Random(1L);
		ByVotesRankMethod sut = new ByVotesRankMethod();

		final Result.ResultType expectedType = Result.ResultType.MULTIPLE;
		for (int i = 0; i < 1; i++) {
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
				Tally tally = TallyBuilder.aNew().with(current.toArray(new Candidate[current.size()])).build();

				final Result result = sut.process(tally, properties, null);
				final Result.ResultType actualType = result.getType();
				if (actualType != expectedType) {
					fail("Expected a " + expectedType + ", but was a "+ actualType + " on input: ");
					printResult(result);
				}
				assertEquals(expectedType, actualType);
				assertEquals(1, result.getNumerOfSeats());
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
		final Candidate candidateA = new Candidate("A", 150);
		final Candidate candidateB = new Candidate("B", 150);
		final Candidate c = new Candidate("C", 75);
		final Candidate d = new Candidate("D", 200);

		final Tally tally = TallyBuilder.aNew().with(candidateA, candidateB, c, d).build();
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
