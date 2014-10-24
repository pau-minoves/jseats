package org.jseats.model.result;

import javax.xml.bind.annotation.XmlRootElement;

import org.jseats.model.Candidate;

@XmlRootElement
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
