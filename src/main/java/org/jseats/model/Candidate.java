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

import org.jseats.xml.XML2PropertiesAdapter;

/**
 * A Candidate object represents a voting option in an election (and for the
 * purpose of seat allocation, in a tally sheet). Depending on the electoral
 * system, a Candidate object may map to an answer, person, party or
 * combination.
 * 
 * Candidate objects are unique on a tally and the property name is used as a
 * key for this purposes. This means that for the purposes of most code, two
 * different Candidate java objects with the same name are the same votable
 * option.
 * 
 */
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

	/**
	 * Create a candidate object without name, votes or properties.
	 * 
	 * This constructor is here to allow for de-serialization for Candidate
	 * objects and is better to avoid it programmatically.
	 */
	public Candidate() {

		this.hasVotes = false;
		properties = new Properties();
	}

	/**
	 * Create a candidate with the provided name, but without votes and
	 * properties.
	 * 
	 * @param name
	 *            the name that uniquely identifies this voting option.
	 */
	public Candidate(String name) {

		this.name = name;
		this.hasVotes = false;
		properties = new Properties();
	}

	/**
	 * Create a candidate with the provided name and votes, but without
	 * properties.
	 * 
	 * @param name
	 *            the name that uniquely identifies this voting option.
	 * @param votes
	 *            casted votes for this candidate
	 */
	public Candidate(String name, int votes) {

		this.name = name;
		this.hasVotes = true;
		this.votes = votes;
		properties = new Properties();
	}

	/**
	 * Get the number of casted votes for this candidate.
	 * 
	 * @return casted votes for this candidate
	 */
	public int getVotes() {
		return votes;
	}

	/**
	 * Get the number of casted votes for this candidate.
	 * 
	 * @param votes
	 *            casted votes for this candidate
	 */
	public void setVotes(int votes) {
		this.hasVotes = true;
		this.votes = votes;
	}

	/**
	 * Get the candidate's name. This name is unique.
	 * 
	 * @return the candidate's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the candidate's name. This name must be unique.
	 * 
	 * @param name
	 *            the candidate's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * True if votes have been set via the constructor or the setVotes method.
	 * Use this instead of getVotes()==0 as 0 is the default value for votes.
	 * 
	 * @return if votes have been set for this instance.
	 */
	public boolean hasVotes() {
		return hasVotes;
	}

	/**
	 * Set a property for this candidate.
	 * 
	 * @param key
	 *            the property key
	 * @param value
	 *            the property value
	 * @return the previous value for this property, if any.
	 */
	public String setProperty(String key, String value) {
		if (value == null) {
			String previous = properties.getProperty(key);
			properties.remove(key);
			return previous;
		}

		return (String) properties.setProperty(key, value);
	}

	/**
	 * Get a property value given the property key.
	 * 
	 * @param key
	 *            the property key
	 * @return the property value
	 */
	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	/**
	 * Return true if the property specified by key equals the provided value.
	 * 
	 * @param key
	 *            the property key
	 * @param value
	 *            the property value
	 * @return true if the property specified by key equals the provided value.
	 */
	public boolean propertyIs(String key, String value) {
		if (properties.containsKey(key)
				&& properties.getProperty(key).contentEquals(value))
			return true;
		else
			return false;
	}

	/**
	 * Manually set that votes have been set on this instance, instead of
	 * relying in a constructor or the setVotes(int) method.
	 * 
	 * @param hasVotes
	 */
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

	/**
	 * Initialize a Candidate object given a Candidate string. The String must
	 * conform to a given format. Examples or valid formats are:
	 * 
	 * Name <br>
	 * Name:200 <br>
	 * Name:200:key1=value:key2=value2: ...
	 * 
	 * @param str
	 *            the candidate string
	 * @return a Candidate object
	 * @throws SeatAllocationException
	 */
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
