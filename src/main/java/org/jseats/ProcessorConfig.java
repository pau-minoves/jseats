package org.jseats;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jseats.model.Candidate;
import org.jseats.model.ResultDecorator;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.SeatAllocationMethod;
import org.jseats.model.Tally;
import org.jseats.model.TallyFilter;
import org.jseats.model.XML2PropertiesAdapter;
import org.jseats.model.tie.TieBreaker;

@XmlRootElement(name = "processor-config")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProcessorConfig {

	@XmlJavaTypeAdapter(XML2PropertiesAdapter.class)
	Properties properties;

	@XmlTransient
	SeatAllocationMethod method;

	@XmlAttribute(name = "method")
	String methodName;

	@XmlElement
	Tally tally;

	@XmlElementWrapper(name = "tally-filters")
	@XmlAnyElement
	List<TallyFilter> filters;

	@XmlElementWrapper(name = "result-decorators")
	@XmlAnyElement
	List<ResultDecorator> decorators;

	static JAXBContext jc;
	static Marshaller marshaller;
	static Unmarshaller unmarshaller;

	@XmlTransient
	private TieBreaker tieBreaker;

	@XmlAttribute(name = "tie-breaker")
	String tieBreakerName;

	public ProcessorConfig() {

		properties = new Properties();
		filters = new ArrayList<TallyFilter>();
		decorators = new ArrayList<ResultDecorator>();
	}

	public void resolveReferences(SeatAllocatorResolver resolver)
			throws SeatAllocationException {

		if (methodName != null)
			this.method = resolver.resolveMethod(methodName);

		if (tieBreakerName != null)
			this.tieBreaker = resolver.resolveTieBreaker(tieBreakerName);
	}

	/*
	 * Tally
	 */

	public Tally getTally() {
		return tally;
	}

	public void setTally(Tally tally) {
		this.tally = tally;
	}

	public boolean addTallyFilter(TallyFilter filter) {
		return filters.add(filter);
	}

	public List<TallyFilter> getTallyFilters() {
		return filters;
	}

	public boolean removeTallyFilter(TallyFilter filter) {
		return filters.remove(filter);
	}

	/*
	 * Result
	 */

	public boolean addResultDecorator(ResultDecorator decorator) {
		return decorators.add(decorator);
	}

	public List<ResultDecorator> getResultDecorator() {
		return decorators;
	}

	public boolean removeResultDecorator(ResultDecorator decorator) {
		return decorators.remove(decorator);
	}

	/*
	 * Tie Breaker
	 */

	public TieBreaker getTieBreaker() {
		return tieBreaker;
	}

	public void setTieBreaker(TieBreaker tieBreaker) {
		this.tieBreaker = tieBreaker;
	}

	public void setTieBreakerName(String tieBreak) {
		this.tieBreakerName = tieBreak;
	}

	/*
	 * Properties
	 */

	public void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public Properties getProperties() {
		return properties;
	}

	/*
	 * Method
	 */

	public void setMethod(SeatAllocationMethod method) {
		this.method = method;
	}

	public SeatAllocationMethod getMethod() {
		return method;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public void reset() {
		properties.clear();
		filters.clear();
		decorators.clear();
		method = null;
		tieBreaker = null;
		tally = null;
	}

	/*
	 * Serialization
	 */

	public void toXML(OutputStream out) throws JAXBException {

		if (jc == null)
			jc = JAXBContext.newInstance(ProcessorConfig.class);

		if (marshaller == null) {
			marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		}

		marshaller.marshal(this, out);
	}

	public static ProcessorConfig fromXML(InputStream is) throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance(ProcessorConfig.class);

		if (unmarshaller == null)
			unmarshaller = jc.createUnmarshaller();

		return (ProcessorConfig) unmarshaller.unmarshal(is);
	}

	@Override
	public String toString() {

		StringBuilder str = new StringBuilder("ProcessorConfig:\n");

		str.append("\tMethod: ");
		str.append(method.toString());
		str.append("\n");

		str.append("\tTally (effective: ");
		str.append(tally.getEffectiveVotes());
		str.append(", potential: ");
		str.append(tally.getPotentialVotes());
		str.append("):\n");

		for (Candidate candidate : tally.getCandidates()) {
			str.append("\t\t");
			str.append(candidate.toString());
			str.append("\n");
		}

		str.append("\tTie breaker: ");
		str.append((tieBreaker == null) ? "none" : tieBreaker.getName());
		str.append("\n");

		Iterator<Object> i = properties.keySet().iterator();

		str.append("\tProperties:\n");
		if (i.hasNext())
			while (i.hasNext()) {
				Object k = i.next();
				str.append("\t\t");
				str.append(k);
				str.append(" = ");
				str.append(properties.getProperty((String) k));
				str.append("\n");
			}
		else
			str.append("\t\tnone\n");

		str.append("\tTally filters:\n");
		if (filters.size() > 0)
			for (TallyFilter filter : filters) {
				str.append("\t\t");
				str.append(filter);
				str.append("\n");
			}
		else
			str.append("\t\tnone\n");

		str.append("\tResult decorators:\n");
		if (decorators.size() > 0)
			for (ResultDecorator decorator : decorators) {
				str.append("\t\t");
				str.append(decorator);
				str.append("\n");
			}
		else
			str.append("\t\tnone\n");

		return str.toString();
	}
}
