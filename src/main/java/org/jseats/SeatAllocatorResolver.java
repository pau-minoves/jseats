package org.jseats;

import org.jseats.model.ResultDecorator;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.SeatAllocationMethod;
import org.jseats.model.TallyFilter;
import org.jseats.model.tie.TieBreaker;

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

	public TieBreaker resolveTieBreaker(String tieBreakerName)
			throws SeatAllocationException;
}
