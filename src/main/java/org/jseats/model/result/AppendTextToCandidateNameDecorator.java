package org.jseats.model.result;

import javax.xml.bind.annotation.XmlRootElement;

import org.jseats.model.Candidate;

@XmlRootElement
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
