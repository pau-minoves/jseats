package org.jseats.model.algorithms;

import java.util.Properties;

import org.jseats.model.InmutableTally;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SeatAllocationMethod {
	
	static Logger log = LoggerFactory.getLogger(SeatAllocationMethod.class);
	
	public abstract Result process(InmutableTally tally, Properties properties) throws SeatAllocationException;
	
	public static SeatAllocationMethod getByName(String name)
			throws SeatAllocationException {
		
		log.debug("Lookup of algorithm: " + name);

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
		if (name.equalsIgnoreCase("Imperiali"))
			return new ImperialiLargestRemainderMethod();

		log.warn("Lookup of "+name+" failed, launching exception");
		
		throw new SeatAllocationException("No algorithm found with name: " + name);
	}
}
