package org.jseats.jbehave;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import static org.junit.Assert.*;

import org.jbehave.core.annotations.Alias;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jseats.SeatAllocatorProcessor;
import org.jseats.model.Candidate;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.Tally;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Steps {

	static Logger log = LoggerFactory.getLogger(Steps.class);

	Tally tally;
	SeatAllocatorProcessor processor = new SeatAllocatorProcessor();
	Result result;

	/*
	 * GIVEN
	 */

	@Given("empty scenario")
	public void emptyTally() {
		
		tally = null;
		result = null;
		processor.reset();
	}

	@Given("use $algorithm algorithm")
	public void setSeatAllocationAlgorithm(String algorithm)
			throws SeatAllocationException {
	
		processor.setMethodByName(algorithm);
	}

	@Given("algorithm has property $key set to $value")
	public void setAlgorithmProperty(String key, String value) {
		log.debug("Setting property " + key + " to value " + value);
		processor.setProperty(key, value);
	}

	@Given("tally has candidate $candidate with $votes votes")
	public void setCandidateInTally(String candidate, int votes) {
		if (tally == null)
			tally = new Tally();

		tally.addCandidate(new Candidate(candidate, votes));
	}
	
	@Given("tally has $votes potential votes")
	public void setPotentialVotesInTally(int votes) {
		if (tally == null)
			tally = new Tally();

		tally.setPotentialVotes(votes);
	}

	/*
	 * WHEN
	 */

	@When("process with $method method")
	@Alias("process with $method algorithm")
	public void processWithAlgorithm(String method)
			throws SeatAllocationException {

		log.debug("Processing with properties: " + processor.getProperties());

		processor.setTally(tally);
		
		setSeatAllocationAlgorithm(method);
		
		result = processor.process();
	}

	@When("load $tally tally")
	public void loadTally(String tally) throws FileNotFoundException,
			JAXBException {

		this.tally = Tally.fromXML(new FileInputStream(tally));
	}

	/*
	 * THEN
	 */

	@Then("result type is $type")
	public void resultTypeIs(String type) {
		log.debug("result.type=" + result.getType());
		assertTrue(result.getType().name().equals(type));
	}
	
	@Then("result has $number seats")
	@Alias("result has $number seat")
	public void resultTypeIs(int number) {
		assertEquals(number, result.getNumerOfSeats());
	}

	@Then("result seat #$seat is $candidate")
	public void resultIs(int seat, String candidate) {

		assertEquals(candidate, result.getSeatAt(seat).getName());
	}
	
	@Then("result seat #$seat isn't $candidate")
	public void resultIsNot(int seat, String candidate) {

		assertNotEquals(candidate, result.getSeatAt(seat).getName());
	}
	
	@Then("result seats contain $candidate")
	public void resultCandidatesContain(String candidate)
			throws SeatAllocationException {

		assertTrue(result.containsSeatForCandidate(new Candidate(candidate)));
	}

	@Then("result seats do not contain $candidate")
	public void resultCandidatesNotContain(String candidate)
			throws SeatAllocationException {

		assertFalse(result.containsSeatForCandidate(new Candidate(candidate)));
	}

	@Then("result is as in file $result")
	public void resultIs(String result) throws FileNotFoundException,
			JAXBException {

		// assertFalse(this.result.equals(Result.fromXML(new FileInputStream(result))));
	}
}
