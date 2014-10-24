package org.jseats.model.methods;

import java.util.Properties;

import javax.xml.bind.annotation.XmlRootElement;

import org.jseats.model.SeatAllocationException;
import org.jseats.model.result.Result;
import org.jseats.model.tally.InmutableTally;

@XmlRootElement
public interface SeatAllocationMethod {

	public abstract Result process(InmutableTally tally, Properties properties)
			throws SeatAllocationException;
}
