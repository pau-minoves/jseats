package org.jseats;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.jseats.model.Candidate;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.result.Result;
import org.jseats.model.result.Result.ResultType;
import org.jseats.model.tally.Tally;

public class SeatAllocatorLauncher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			Tally tally = new Tally();

			List<Candidate> candidates = new ArrayList<Candidate>();

			candidates.add(new Candidate("CandidateA", 100));
			candidates.add(new Candidate("CandidateB", 150));
			candidates.add(new Candidate("CandidateC", 200));

			tally.setCandidates(candidates);

			tally.toXML(new FileOutputStream("target/example.tally.xml"));

			Result result = new Result(ResultType.SINGLE);

			result.addSeat(new Candidate("CandidateC", 100));

			result.toXML(new FileOutputStream("target/example.result.xml"));

		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
