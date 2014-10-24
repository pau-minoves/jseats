package org.jseats;

import org.jseats.model.SeatAllocationException;
import org.jseats.model.methods.SeatAllocationMethod;
import org.jseats.model.result.ResultDecorator;
import org.jseats.model.tally.TallyFilter;

public interface SeatAllocatorResolver {

	public String[] listTallyFilters();

	public String[] listResultDecorators();

	public String[] listMethods();

	public SeatAllocationMethod resolveMethod(String method)
			throws SeatAllocationException;

	public TallyFilter resolveTallyFilter(String filter)
			throws SeatAllocationException;

	public ResultDecorator resolveResultDecorator(String decorator)
			throws SeatAllocationException;
}
