package org.jseats.model;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Tally implements InmutableTally {

	static JAXBContext jc;
	static Marshaller marshaller;
	static Unmarshaller unmarshaller;

	@XmlElementWrapper(name = "candidates")
	@XmlElement
	List<Candidate> candidates;
	
	@XmlElement
	int effectiveVotes;
	
	@XmlElement
	int potentialVotes;
	
	public Tally() {
		effectiveVotes = 0;
		candidates = new ArrayList<Candidate>();
	}
	
	@Override
	public int getPotentialVotes() {
		return potentialVotes;
	}

	public void setPotentialVotes(int potentialVotes) {
		this.potentialVotes = potentialVotes;
	}

	@Override
	public int getEffectiveVotes() {
		return effectiveVotes;
	}

	public List<Candidate> getCandidates() {
		return candidates;
	}
	
	@Override
	public Candidate getCandidateAt(int position) {
		return candidates.get(position);
	}

	@Override
	public int getNumerOfCandidates() {
		return candidates.size();
	}

	public void setCandidates(List<Candidate> candidates) {
		
		this.candidates = candidates;
		
		effectiveVotes = 0;
		for(Candidate candidate: candidates)
			effectiveVotes += candidate.getVotes();
	}
	
	public void addCandidate(Candidate candidate) {
			
		this.candidates.add(candidate);
		effectiveVotes += candidate.getVotes();
	}

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
		
		StringBuilder str = new StringBuilder("tally:");
		str.append("C=");
		str.append(candidates.size());
		str.append("eV=");
		str.append(effectiveVotes);
		str.append("=>");
		for(Candidate candidate : candidates){
			str.append(candidate.toString());
			str.append(":");
		}
		
		return str.toString();
	}
}
