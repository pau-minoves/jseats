package org.jseats.model;

import java.util.Iterator;
import java.util.Objects;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class Candidate implements Comparable<Candidate> {

	@XmlAttribute
	String name;

	@XmlAttribute
	int votes;

	@XmlTransient
	boolean hasVotes;

	@XmlJavaTypeAdapter(XML2PropertiesAdapter.class)
	Properties properties;

	public Candidate() {
		this.hasVotes = true;
		properties = new Properties();
	}

	public Candidate(String name) {

		this.name = name;
		this.hasVotes = false;
		properties = new Properties();
	}

	public Candidate(String name, int votes) {

		this.name = name;
		this.hasVotes = true;
		this.votes = votes;
		properties = new Properties();
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.hasVotes = true;
		this.votes = votes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean hasVotes() {
		return hasVotes;
	}

	public String setProperty(String key, String value) {
		if (value == null) {
			String previous = properties.getProperty(key);
			properties.remove(key);
			return previous;
		}

		return (String) properties.setProperty(key, value);
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public boolean propertyIs(String key, String value) {
		if (properties.containsKey(key)
				&& properties.getProperty(key).contentEquals(value))
			return true;
		else
			return false;
	}

	public void hasVotes(boolean hasVotes) {
		this.hasVotes = hasVotes;
	}

	public int compareTo(Candidate other) {
		return other.votes - votes;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder(name);
		str.append(":");
		str.append(votes);

		if (!properties.isEmpty()) {
			Iterator<Object> i = properties.keySet().iterator();

			while (i.hasNext()) {
				String key = (String) i.next();
				str.append(":" + key + "=" + properties.getProperty(key));
			}
		}

		return str.toString();
	}

	public static Candidate fromString(String str)
			throws SeatAllocationException {

		StringTokenizer st = new StringTokenizer(str, ":=");

		String name = st.nextToken();
		String votes = st.nextToken();

		Candidate candidate;

		try {
			candidate = new Candidate(name);
			candidate.setVotes(Integer.parseInt(votes));

			while (st.countTokens() >= 2) {
				candidate.setProperty(st.nextToken(), st.nextToken());
			}

		} catch (Exception e) {
			throw new SeatAllocationException(
					"Candidate must be of the form Name:votes[:key=value] "
							+ e.getMessage());
		}

		return candidate;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Candidate) {
			return this.name.contentEquals(((Candidate) other).name);
		} else
			return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

}
