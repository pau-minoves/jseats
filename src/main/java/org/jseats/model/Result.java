package org.jseats.model;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@XmlRootElement
public class Result {

	public enum ResultType {
		
		SINGLE("single-result"),
		MULTIPLE("multiple-result"),
		TIE("tie"), 
		UNDECIDED("undecided");
		
		private final String type;
		private ResultType(String name) {
			this.type = name;
		}
	}
	
	@XmlAttribute
	ResultType type;
	
	static Logger log = LoggerFactory.getLogger(Result.class);

	static JAXBContext jc;
	static Marshaller marshaller;
	static Unmarshaller unmarshaller;

	@XmlElementWrapper(name = "candidates")
	@XmlElement
	List<Candidate> candidates;

	public Result(ResultType type) {
		this.type = type;
		
		candidates = new ArrayList<Candidate>();
	}

	public ResultType getType() {
		return type;
	}

	protected void setType(ResultType type) {
		this.type = type;
	}

	private void checkTypeIs(ResultType... types) throws SeatAllocationException {
		for(ResultType type2 : types) {
			if(type.equals(type2))
				return;
		}
		
		throw new SeatAllocationException("Invalid operation on result of type "+ type);
	}
	
	public List<Candidate> getCandidates() {
		return candidates;
	}

	public void setCandidates(List<Candidate> candidates) {
		this.candidates = candidates;
	}

	public Candidate getCandidate() throws SeatAllocationException {
		checkTypeIs(ResultType.SINGLE);
		return candidates.get(0);
	}

	public void addCandidate(Candidate candidate) {
		this.candidates.add(candidate);
	}
	
	public void setCandidate(Candidate candidate) throws SeatAllocationException {
		checkTypeIs(ResultType.SINGLE);
		this.candidates.add(0, candidate);
	}
	
	public boolean containsCandidate(Candidate candidate) {
		
		for (Candidate resultCandidate : candidates) {
			if (resultCandidate.equals(candidate))
				return true;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		
		StringBuilder str = new StringBuilder("result(");
		str.append(type);
		str.append("):C=");
		str.append(candidates.size());
		str.append("=>");
		for(Candidate candidate : candidates){
			str.append(candidate.toString());
			str.append(":");
		}
		
		return str.toString();
	}
	
	public void toXML(OutputStream out) throws JAXBException {

		log.debug("Marshalling " + this + " to " + out);

		if (jc == null)
			jc = JAXBContext.newInstance(Result.class);

		if (marshaller == null) {
			marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		}

		marshaller.marshal(this, out);
	}

	public static Result fromXML(InputStream is) throws JAXBException {

		log.debug("Unmarshalling from " + is);

		if (jc == null)
			jc = JAXBContext.newInstance(Result.class);

		if (unmarshaller == null)
			unmarshaller = jc.createUnmarshaller();

		return (Result) unmarshaller.unmarshal(is);
	}

}
