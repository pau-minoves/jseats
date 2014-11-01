package org.jseats;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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

import org.jseats.model.ResultDecorator;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.SeatAllocationMethod;
import org.jseats.model.Tally;
import org.jseats.model.TallyFilter;

@XmlRootElement(name = "processor-config")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProcessorConfig {

	@XmlElement
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

	public ProcessorConfig() {

		properties = new Properties();
		filters = new ArrayList<TallyFilter>();
		decorators = new ArrayList<ResultDecorator>();
	}

	public void resolveReferences(SeatAllocatorResolver resolver)
			throws SeatAllocationException {

		if (methodName != null)
			this.method = resolver.resolveMethod(methodName);
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

		StringBuilder str = new StringBuilder("ProcessorConfig:");

		// TODO complete

		return str.toString();
	}
}
