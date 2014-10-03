package org.jseats;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.jseats.model.SeatAllocationException;
import org.jseats.model.methods.SeatAllocationMethod;
import org.jseats.model.result.Result;
import org.jseats.model.result.ResultDecorator;
import org.jseats.model.tally.InmutableTally;
import org.jseats.model.tally.Tally;
import org.jseats.model.tally.TallyFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeatAllocatorProcessor {

	Logger log = LoggerFactory.getLogger(SeatAllocatorProcessor.class);

	Properties properties;

	SeatAllocationMethod method;

	Tally tally;

	List<TallyFilter> filters;
	List<ResultDecorator> decorators;

	/*
	 * Tally
	 */
	public SeatAllocatorProcessor() {
		log.debug("Initializing processor");
		properties = new Properties();

		filters = new ArrayList<TallyFilter>();
		decorators = new ArrayList<ResultDecorator>();
	}

	public InmutableTally getTally() {
		return tally;
	}

	public void setTally(Tally tally) {
		log.debug("Added tally: " + tally);
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
		log.debug("Set property " + key + "=" + value);
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

	public void setMethodByName(String method) throws SeatAllocationException {

		log.debug("Adding method by name:" + method);
		this.method = SeatAllocationMethod.getByName(method);
	}

	public void setMethodByClass(Class<? extends SeatAllocationMethod> clazz)
			throws InstantiationException, IllegalAccessException {

		log.debug("Adding method by class:" + clazz);
		method = clazz.newInstance();
	}

	/*
	 * Processor
	 */

	public void reset() {
		log.debug("Resetting processor");

		properties.clear();
		filters.clear();
		decorators.clear();
		method = null;
		tally = null;
	}

	public Result process() throws SeatAllocationException {

		if (tally == null)
			throw new SeatAllocationException(
					"Trying to run processor without providing a tally");

		if (!filters.isEmpty()) {
			log.debug("Executing filters");
			for (TallyFilter filter : filters) {
				log.trace("Executing filter: " + filter);
				tally = filter.filter(tally);
			}
		}
		else
			log.debug("No tally filters to execute");

		log.debug("Processing...");

		Result result = method.process(tally, properties);

		log.debug("Processed");

		if (!filters.isEmpty()) {
			log.debug("Executing decorators");
			for (ResultDecorator decorator : decorators) {
				log.trace("Executing decorator: " + decorator);
				result = decorator.decorate(result);
			}
		}
		else
			log.debug("No result decorators to execute");
		
		return result;

	}
}
