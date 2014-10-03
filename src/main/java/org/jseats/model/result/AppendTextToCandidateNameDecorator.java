package org.jseats.model.result;

import java.util.Iterator;

import org.jseats.model.Candidate;

public class AppendTextToCandidateNameDecorator implements ResultDecorator {

	String text;

	public AppendTextToCandidateNameDecorator(String text) {
		this.text = text;
	}

	@Override
	public Result decorate(Result result) {

		for (Candidate candidate : result.getSeats())
			candidate.setName(candidate.getName() + text);

		return result;
	}

}
