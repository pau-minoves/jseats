package org.jseats.model.result;

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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.jseats.model.Candidate;
import org.jseats.model.SeatAllocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Result {

	public enum ResultType {

		SINGLE("single-result"), MULTIPLE("multiple-result"), TIE("tie"), UNDECIDED(
				"undecided");

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

	@XmlElementWrapper(name = "seats")
	@XmlElement(name = "seat")
	List<Candidate> seats;

	public Result() {
		seats = new ArrayList<Candidate>();
	}

	public Result(ResultType type) {
		this.type = type;

		seats = new ArrayList<Candidate>();
	}

	public ResultType getType() {
		return type;
	}

	protected void setType(ResultType type) {
		this.type = type;
	}

	private void checkTypeIs(ResultType... types)
			throws SeatAllocationException {
		for (ResultType type2 : types) {
			if (type.equals(type2))
				return;
		}

		throw new SeatAllocationException(
				"Invalid operation on result of type " + type);
	}

	public int getNumerOfSeats() {
		return seats.size();
	}

	public List<Candidate> getSeats() {
		return seats;
	}

	public Candidate getSeatAt(int position) {
		return seats.get(position);
	}

	public void addSeat(Candidate candidate) {
		this.seats.add(candidate);
	}

	public void setSeats(List<Candidate> candidates) {
		this.seats = candidates;
	}

	public boolean containsSeatForCandidate(Candidate candidate) {

		for (Candidate seat : seats) {
			if (seat.equals(candidate))
				return true;
		}

		return false;
	}

	public boolean containsSeatForCandidate(String candidate) {

		for (Candidate seat : seats) {
			if (seat.getName().contentEquals(candidate))
				return true;
		}

		return false;
	}

	@Override
	public String toString() {

		StringBuilder str = new StringBuilder("result(");
		str.append(type);
		str.append("):C=");
		str.append(seats.size());
		str.append("=>");
		for (Candidate seat : seats) {
			str.append(seat.toString());
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
