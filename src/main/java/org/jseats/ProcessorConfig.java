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
import org.jseats.model.tie.TieBreaker;
import org.jseats.xml.XML2PropertiesAdapter;

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
	@XmlElement(name = "filter")
	List<String> filterNames;
	@XmlTransient
	List<TallyFilter> filters;

	@XmlElementWrapper(name = "result-decorators")
	@XmlElement(name = "decorator")
	List<String> decoratorNames;
	@XmlTransient
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
		filterNames = new ArrayList<String>();

		decorators = new ArrayList<ResultDecorator>();
		decoratorNames = new ArrayList<String>();
	}

	/**
	 * Given the names of methods, tie breakers, filter names and decorators,
	 * use the provided SeatAllocationResolver to fetch the appropriate
	 * instances.
	 * 
	 * @param resolver
	 * @throws SeatAllocationException
	 *             as in SeatAllocationResolver
	 */
	public void resolveReferences(SeatAllocatorResolver resolver) throws SeatAllocationException {

		if (methodName != null)
			this.method = resolver.resolveMethod(methodName);

		if (tieBreakerName != null)
			this.tieBreaker = resolver.resolveTieBreaker(tieBreakerName);

		if (filterNames.size() > 0)
			for (String filter : filterNames)
				filters.add(resolver.resolveTallyFilter(filter));

		if (decoratorNames.size() > 0)
			for (String decorator : decoratorNames)
				decorators.add(resolver.resolveResultDecorator(decorator));
	}

	/*
	 * Tally
	 */

	/**
	 * Obtain the Tally configured in this processor configuration.
	 * 
	 * @return the tally instance.
	 */
	public Tally getTally() {
		return tally;
	}

	/**
	 * Set the Tally to configure in this processor configuration.
	 * 
	 * @param tally
	 *            the tally instance.
	 */
	public void setTally(Tally tally) {
		this.tally = tally;
	}

	/**
	 * Add a {@link TallyFilter} instance to this processor configuration.
	 * 
	 * @param filter
	 *            the tally filter instance.
	 * @return true if filter has been successfully added, false otherwise.
	 */
	public boolean addTallyFilter(TallyFilter filter) {
		filterNames.add(filter.getName());
		return filters.add(filter);
	}

	/**
	 * Get the {@link TallyFilter} instances from this processor configuration.
	 * 
	 * @return a list of the TallyFilter instances.
	 */
	public List<TallyFilter> getTallyFilters() {
		return filters;
	}

	/**
	 * Remove a {@link TallyFilter} instance from the internal chain of filters.
	 * 
	 * @param filter
	 * @return true if successfully removed
	 */
	public boolean removeTallyFilter(TallyFilter filter) {
		return filters.remove(filter);
	}

	/*
	 * Result
	 */

	public boolean addResultDecorator(ResultDecorator decorator) {
		decoratorNames.add(decorator.getName());
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

	/**
	 * Set the {@link SeatAllocationMethod} object used in this processor
	 * configuration.
	 * 
	 * @param method
	 *            {@link SeatAllocationMethod}
	 */
	public void setMethod(SeatAllocationMethod method) {
		this.method = method;
	}

	/**
	 * Obtain the {@link SeatAllocationMethod} configured in this processor
	 * configuration instance.
	 * 
	 * @return the {@link SeatAllocationMethod} instance.
	 */
	public SeatAllocationMethod getMethod() {
		return method;
	}

	/**
	 * Obtain the friendly name associated to the {@link SeatAllocationMethod}
	 * provided with <code>setMethod({@link SeatAllocationMethod})</code>
	 * 
	 * @return a string with the friendly name.
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * Provide a friendly name to be used by the internal resolver to fetch the
	 * appropriate {@link SeatAllocationMethod} instance later.
	 * 
	 * @param methodName
	 *            the method name to provide to the resolver.
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * Reset this processor configuration internal state and leave it as brand
	 * new.
	 */
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

	/**
	 * Serialize this processor configuration into the provided InputStream as
	 * an XML.
	 * 
	 * @param out
	 * 
	 * @throws JAXBException
	 *             on marshaller error.
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

	/**
	 * Serialize a processor configuration from the provided InputStream as an
	 * XML.
	 * 
	 * @param is
	 * @return {@link ProcessorConfig}
	 * @throws JAXBException
	 */
	public static ProcessorConfig fromXML(InputStream is) throws JAXBException {
		if (jc == null)
			jc = JAXBContext.newInstance(ProcessorConfig.class);

		if (unmarshaller == null)
			unmarshaller = jc.createUnmarshaller();

		return (ProcessorConfig) unmarshaller.unmarshal(is);
	}

	/**
	 * Build a string describing the internal state of this processor
	 * configuration.
	 */
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
