package org.jseats.model.result;

import java.util.Iterator;

import org.jseats.model.Candidate;

public class SuffixTextToCandidateNameDecorator implements ResultDecorator {

	String text;

	public SuffixTextToCandidateNameDecorator(String text) {
		this.text = text;
	}

	@Override
	public Result decorate(Result result) {

		for (Candidate candidate : result.getSeats())
			candidate.setName(text + candidate.getName());

		return result;
	}

}
