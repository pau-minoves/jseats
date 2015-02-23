package org.jseats.model.result;

import javax.xml.bind.annotation.XmlRootElement;

import org.jseats.model.Candidate;
import org.jseats.model.Result;
import org.jseats.model.ResultDecorator;

@XmlRootElement
public class SuffixTextToCandidateNameDecorator implements ResultDecorator {

	@Override
	public String getName() {
		return "suffix-text-to-candidate-name-decorator";
	}

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
