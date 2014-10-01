package org.jseats.model.methods;

import java.util.Properties;

import org.jseats.model.InmutableTally;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SeatAllocationMethod {

	static Logger log = LoggerFactory.getLogger(SeatAllocationMethod.class);

	public abstract Result process(InmutableTally tally, Properties properties)
			throws SeatAllocationException;

	public static SeatAllocationMethod getByName(String name)
			throws SeatAllocationException {

		log.debug("Lookup of method: " + name);

		if (name.equalsIgnoreCase("SimpleMajority"))
			return new SimpleMajorityMethod();
		if (name.equalsIgnoreCase("AbsoluteMajority"))
			return new AbsoluteMajorityMethod();
		if (name.equalsIgnoreCase("QualifiedMajority"))
			return new QualifiedMajorityMethod();
		if (name.equalsIgnoreCase("Hare"))
			return new HareLargestRemainderMethod();
		if (name.equalsIgnoreCase("Droop"))
			return new DroopLargestRemainderMethod();
		if (name.equalsIgnoreCase("Imperiali-lr"))
			return new ImperialiLargestRemainderMethod();
		if (name.equalsIgnoreCase("DHondt"))
			return new DHondtHighestAveragesMethod();
		if (name.equalsIgnoreCase("Sainte-Lague"))
			return new SainteLagueHighestAveragesMethod();
		if (name.equalsIgnoreCase("Imperiali-ha"))
			return new DHondtHighestAveragesMethod();
		if (name.equalsIgnoreCase("Huntington-Hill"))
			return new HuntingtonHillHighestAveragesMethod();
		if (name.equalsIgnoreCase("Danish"))
			return new DanishHighestAveragesMethod();

		log.warn("Lookup of " + name + " failed, launching exception");

		throw new SeatAllocationException("No method found with name: " + name);
	}
}
