package org.jseats.model.result;

import java.util.Properties;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.jseats.model.Candidate;
import org.jseats.model.Result;
import org.jseats.model.ResultDecorator;

/**
 * This {@link ResultDecorator} will append the provided text to the candidate
 * name. The text to append is provided as a property "text" inside a
 * {@link Properties} object.
 *
 */
@XmlRootElement
public class AppendTextToCandidateNameDecorator implements ResultDecorator {

	@Override
	@XmlAttribute(name = "name")
	public String getName() {

		StringBuffer sb = new StringBuffer();
		sb.append("append-text-to-candidate-name-decorator:");
		sb.append("text=");
		sb.append(properties.getProperty("text"));

		return sb.toString();
	}

	Properties properties;

	/**
	 * Initialize this {@link AppendTextToCandidateNameDecorator} with the
	 * provided {@link Properties} object. This implementation will look for a
	 * property with key "text" to fetch the text to append.
	 * 
	 * @param properties
	 */
	public AppendTextToCandidateNameDecorator(Properties properties) {
		this.properties = properties;
	}

	/**
	 * Initialize this {@link AppendTextToCandidateNameDecorator} with the
	 * provided text.
	 * 
	 * @param text
	 */
	@Deprecated
	public AppendTextToCandidateNameDecorator(String text) {
		this.properties = new Properties();
		this.properties.setProperty("text", text);
	}

	@Override
	public Result decorate(Result result) {

		for (Candidate candidate : result.getSeats())
			candidate.setName(candidate.getName() + properties.getProperty("text"));

		return result;
	}
}
