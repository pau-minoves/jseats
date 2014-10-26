package org.jseats.model.result;

import javax.xml.bind.annotation.XmlRootElement;

import org.jseats.model.Candidate;
import org.jseats.model.Result;
import org.jseats.model.ResultDecorator;

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
