package org.jseats.model.algorithms;

import java.util.Properties;

import org.jseats.model.InmutableTally;
import org.jseats.model.Result;
import org.jseats.model.SeatAllocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SeatAllocationAlgorithm {
	
	static Logger log = LoggerFactory.getLogger(SeatAllocationAlgorithm.class);
	
	public abstract Result process(InmutableTally tally, Properties properties) throws SeatAllocationException;
	
	public static SeatAllocationAlgorithm getByName(String name)
			throws SeatAllocationException {
		
		log.debug("Lookup of algorithm: " + name);

		if (name.equalsIgnoreCase("SimpleMajority"))
			return new SimpleMajorityAlgorithm();
		if (name.equalsIgnoreCase("AbsoluteMajority"))
			return new AbsoluteMajorityAlgorithm();
		if (name.equalsIgnoreCase("QualifiedMajority"))
			return new QualifiedMajorityAlgorithm();
		if (name.equalsIgnoreCase("Hare"))
			return new HareLargestRemainderAlgorithm();
		if (name.equalsIgnoreCase("Droop"))
			return new DroopLargestRemainderAlgorithm();		
		if (name.equalsIgnoreCase("Imperiali"))
			return new ImperialiLargestRemainderAlgorithm();

		log.warn("Lookup of "+name+" failed, launching exception");
		
		throw new SeatAllocationException("No algorithm found with name: " + name);
	}
}
