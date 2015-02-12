package org.jseats.model;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tally")
@XmlAccessorType(XmlAccessType.FIELD)
public class Tally implements ImmutableTally {

	static JAXBContext jc;
	static Marshaller marshaller;
	static Unmarshaller unmarshaller;

	@XmlElementWrapper(name = "candidates")
	@XmlElement(name = "candidate")
	List<Candidate> candidates;

	@XmlElement
	int effectiveVotes;

	@XmlElement
	int potentialVotes = -1;

	public Tally() {
		effectiveVotes = 0;
		candidates = new ArrayList<Candidate>();
	}

	/*
	 * Votes
	 */
	@Override
	public int getPotentialVotes() {
		return (potentialVotes == -1) ? effectiveVotes : potentialVotes;
	}

	public void setPotentialVotes(int potentialVotes) {
		this.potentialVotes = potentialVotes;
	}

	@Override
	public int getEffectiveVotes() {
		return effectiveVotes;
	}

	/*
	 * Candidates
	 */
	public List<Candidate> getCandidates() {
		return candidates;
	}

	@Override
	public Candidate getCandidateAt(int position) {
		return candidates.get(position);
	}

	@Override
	public int getCandidateIndex(Candidate candidate) {
		for (int i = 0; i < candidates.size(); i++) {
			if (candidates.get(i).equals(candidate))
				return i;
		}
		return -1;
	}

	@Override
	public int getNumberOfCandidates() {
		return candidates.size();
	}

	public void addCandidate(Candidate candidate) {
		this.candidates.add(candidate);
		effectiveVotes += candidate.getVotes();
	}

	public void removeCandidate(Candidate candidate) {
		candidates.remove(candidate);
		effectiveVotes -= candidate.getVotes();
	}

	/*
	 * Serialization
	 */
	public void toXML(OutputStream out) throws JAXBException {

		if (jc == null)
			jc = JAXBContext.newInstance(Tally.class);

		if (marshaller == null) {
			marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		}

		marshaller.marshal(this, out);
	}

	public static Tally fromXML(InputStream is) throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance(Tally.class);

		if (unmarshaller == null)
			unmarshaller = jc.createUnmarshaller();

		return (Tally) unmarshaller.unmarshal(is);
	}

	@Override
	public String toString() {

		StringBuilder str = new StringBuilder("tally (");
		str.append(effectiveVotes);
		str.append("/");
		str.append(potentialVotes);
		str.append(") with ");
		str.append(candidates.size());
		str.append(" candidates: ");
		for (Candidate candidate : candidates) {
			str.append(candidate.toString());
			str.append(", ");
		}
		str.delete(str.length() - 2, str.length());
		str.append(".");
		return str.toString();
	}
}
