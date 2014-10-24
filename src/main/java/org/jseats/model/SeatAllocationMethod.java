package org.jseats.model;

import java.util.Properties;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public interface SeatAllocationMethod {

	public abstract Result process(InmutableTally tally, Properties properties)
			throws SeatAllocationException;
}
