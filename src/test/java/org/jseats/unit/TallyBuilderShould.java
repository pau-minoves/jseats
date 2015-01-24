package org.jseats.unit;

import org.jseats.model.Candidate;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by alvaro on 24/01/15.
 */
public class TallyBuilderShould {

	@Test
	public void create_a_tally_with_no_candidates() {
		assertThat(TallyBuilder.aNew().build().getNumberOfCandidates(), is(0));
	}

	@Test
	public void create_a_tally_with_one_candidate() {

		assertThat(TallyBuilder.aNew().with(new Candidate()).build().getNumberOfCandidates(), is(1));
	}

	@Test
	public void create_a_tally_with_many_candidates() {
		assertThat(TallyBuilder.aNew().with(new Candidate(), new Candidate()).build().getNumberOfCandidates(), is(2));
	}
}
