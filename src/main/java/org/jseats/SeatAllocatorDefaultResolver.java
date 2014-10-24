package org.jseats;

import java.util.HashMap;

import org.jseats.model.SeatAllocationException;
import org.jseats.model.methods.AbsoluteMajorityMethod;
import org.jseats.model.methods.DHondtHighestAveragesMethod;
import org.jseats.model.methods.DanishHighestAveragesMethod;
import org.jseats.model.methods.DroopLargestRemainderMethod;
import org.jseats.model.methods.HareLargestRemainderMethod;
import org.jseats.model.methods.HuntingtonHillHighestAveragesMethod;
import org.jseats.model.methods.ImperialiLargestRemainderMethod;
import org.jseats.model.methods.QualifiedMajorityMethod;
import org.jseats.model.methods.SainteLagueHighestAveragesMethod;
import org.jseats.model.methods.SeatAllocationMethod;
import org.jseats.model.methods.SimpleMajorityMethod;
import org.jseats.model.result.AppendTextToCandidateNameDecorator;
import org.jseats.model.result.NullResultDecorator;
import org.jseats.model.result.ResultDecorator;
import org.jseats.model.result.SuffixTextToCandidateNameDecorator;
import org.jseats.model.tally.NullTallyFilter;
import org.jseats.model.tally.RemoveCandidatesBelow;
import org.jseats.model.tally.TallyFilter;

public class SeatAllocatorDefaultResolver implements SeatAllocatorResolver {

	HashMap<String, Class<? extends SeatAllocationMethod>> methods = new HashMap<>();
	HashMap<String, Class<? extends TallyFilter>> filters = new HashMap<>();
	HashMap<String, Class<? extends ResultDecorator>> decorators = new HashMap<>();

	public SeatAllocatorDefaultResolver() {

		methods.put("SimpleMajority", SimpleMajorityMethod.class);
		methods.put("AbsoluteMajority", AbsoluteMajorityMethod.class);
		methods.put("QualifiedMajority", QualifiedMajorityMethod.class);
		methods.put("Hare", HareLargestRemainderMethod.class);
		methods.put("Droop", DroopLargestRemainderMethod.class);
		methods.put("Imperiali-lr", ImperialiLargestRemainderMethod.class);
		methods.put("DHondt", DHondtHighestAveragesMethod.class);
		methods.put("Sainte-Lague", SainteLagueHighestAveragesMethod.class);
		methods.put("Imperiali-ha", DHondtHighestAveragesMethod.class);
		methods.put("Huntington-Hill",
				HuntingtonHillHighestAveragesMethod.class);
		methods.put("Danish", DanishHighestAveragesMethod.class);

		filters.put("RemoveCandidatesBelow", RemoveCandidatesBelow.class);
		filters.put("NullTallyFilter", NullTallyFilter.class);

		decorators.put("AppendTextToCandidateName",
				AppendTextToCandidateNameDecorator.class);
		decorators.put("SuffixTextToCandidateNameDecorator",
				SuffixTextToCandidateNameDecorator.class);
		decorators.put("NullResultDecorator", NullResultDecorator.class);
	}

	@Override
	public String[] listTallyFilters() {
		return (String[]) filters.keySet().toArray();
	}

	@Override
	public String[] listResultDecorators() {
		return (String[]) decorators.keySet().toArray();
	}

	@Override
	public String[] listMethods() {
		return (String[]) methods.keySet().toArray();
	}

	@SuppressWarnings("unchecked")
	@Override
	public SeatAllocationMethod resolveMethod(String methodName)
			throws SeatAllocationException {

		if (!methods.containsKey(methodName))
			throw new SeatAllocationException(
					"Seat allocation method cannot be resolved: " + methodName);

		try {
			return ((Class<SeatAllocationMethod>) methods.get(methodName))
					.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new SeatAllocationException(
					"Method resolved but cannot be instantiated: "
							+ e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public TallyFilter resolveTallyFilter(String filterName)
			throws SeatAllocationException {

		if (!filters.containsKey(filterName))
			throw new SeatAllocationException(
					"Tally filter cannot be resolved: " + filterName);

		try {
			return ((Class<TallyFilter>) filters.get(filterName)).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new SeatAllocationException(
					"Tally filter resolved but cannot be instantiated: "
							+ e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultDecorator resolveResultDecorator(String decoratorName)
			throws SeatAllocationException {

		if (!decorators.containsKey(decoratorName))
			throw new SeatAllocationException(
					"Result decorator cannot be resolved: " + decoratorName);

		try {
			return ((Class<ResultDecorator>) decorators.get(decoratorName))
					.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new SeatAllocationException(
					"Result decorator resolved but cannot be instantiated: "
							+ e.getMessage());
		}

	}

}
