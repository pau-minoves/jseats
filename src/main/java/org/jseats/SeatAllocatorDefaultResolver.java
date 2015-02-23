package org.jseats;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;

import org.jseats.model.ResultDecorator;
import org.jseats.model.SeatAllocationException;
import org.jseats.model.SeatAllocationMethod;
import org.jseats.model.TallyFilter;
import org.jseats.model.methods.AbsoluteMajorityMethod;
import org.jseats.model.methods.ByVotesRankMethod;
import org.jseats.model.methods.DHondtHighestAveragesMethod;
import org.jseats.model.methods.DanishHighestAveragesMethod;
import org.jseats.model.methods.DroopLargestRemainderMethod;
import org.jseats.model.methods.EqualProportionsMethod;
import org.jseats.model.methods.HareLargestRemainderMethod;
import org.jseats.model.methods.ImperialiLargestRemainderMethod;
import org.jseats.model.methods.QualifiedMajorityMethod;
import org.jseats.model.methods.SainteLagueHighestAveragesMethod;
import org.jseats.model.methods.SimpleMajorityMethod;
import org.jseats.model.result.AppendTextToCandidateNameDecorator;
import org.jseats.model.result.NullResultDecorator;
import org.jseats.model.result.SuffixTextToCandidateNameDecorator;
import org.jseats.model.tally.NullTallyFilter;
import org.jseats.model.tally.RemoveCandidatesBelow;
import org.jseats.model.tie.InteractiveTieBreaker;
import org.jseats.model.tie.MinorityTieBreaker;
import org.jseats.model.tie.RandomTieBreaker;
import org.jseats.model.tie.TieBreaker;

public class SeatAllocatorDefaultResolver implements SeatAllocatorResolver {

	HashMap<String, Class<? extends SeatAllocationMethod>> methods = new HashMap<>();
	HashMap<String, Class<? extends TallyFilter>> filters = new HashMap<>();
	HashMap<String, Class<? extends ResultDecorator>> decorators = new HashMap<>();

	HashMap<String, Class<? extends TieBreaker>> tieBreakers = new HashMap<>();

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
		methods.put("EqualProportions", EqualProportionsMethod.class);
		methods.put("Danish", DanishHighestAveragesMethod.class);
		methods.put("RankByVotes", ByVotesRankMethod.class);

		filters.put("remove-candidates-below-filter",
				RemoveCandidatesBelow.class);
		filters.put("null-filter", NullTallyFilter.class);

		decorators.put("append-text-to-candidate-name-decorator",
				AppendTextToCandidateNameDecorator.class);
		decorators.put("suffix-text-to-candidate-name-decorator",
				SuffixTextToCandidateNameDecorator.class);
		decorators.put("null-decorator", NullResultDecorator.class);

		tieBreakers.put("random-tie-breaker", RandomTieBreaker.class);
		tieBreakers.put("console-tie-breaker", InteractiveTieBreaker.class);
		tieBreakers.put("minority-tie-breaker", MinorityTieBreaker.class);
	}

	@Override
	public String[] listTallyFilters() {
		return (String[]) filters.keySet().toArray(new String[filters.size()]);
	}

	@Override
	public String[] listResultDecorators() {
		return (String[]) decorators.keySet().toArray(
				new String[decorators.size()]);
	}

	@Override
	public String[] listMethods() {
		return (String[]) methods.keySet().toArray(new String[methods.size()]);
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
	public TallyFilter resolveTallyFilter(String filterString)
			throws SeatAllocationException {

		StringTokenizer st = new StringTokenizer(filterString, ":=");
		String filterName = st.nextToken();

		if (!filters.containsKey(filterName))
			throw new SeatAllocationException(
					"Tally filter cannot be resolved: " + filterName);

		Properties props = new Properties();
		while (st.countTokens() >= 2) {
			props.setProperty(st.nextToken(), st.nextToken());
		}

		try {
			if (props.size() > 0) {
				return ((Class<TallyFilter>) filters.get(filterName))
						.getConstructor(Properties.class).newInstance(props);
			} else
				return ((Class<TallyFilter>) filters.get(filterName))
						.newInstance();

		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new SeatAllocationException(
					"Tally filter resolved but cannot be instantiated: "
							+ e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultDecorator resolveResultDecorator(String decoratorString)
			throws SeatAllocationException {

		StringTokenizer st = new StringTokenizer(decoratorString, ":=");
		String decoratorName = st.nextToken();

		if (!decorators.containsKey(decoratorName))
			throw new SeatAllocationException(
					"Result decorator cannot be resolved: " + decoratorName);

		Properties props = new Properties();
		while (st.countTokens() >= 2) {
			props.setProperty(st.nextToken(), st.nextToken());
		}

		try {
			if (props.size() > 0) {
				return ((Class<ResultDecorator>) decorators.get(decoratorName))
						.getConstructor(Properties.class).newInstance(props);
			} else
				return ((Class<ResultDecorator>) decorators.get(decoratorName))
						.newInstance();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new SeatAllocationException(
					"Result decorator resolved but cannot be instantiated: "
							+ e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public TieBreaker resolveTieBreaker(String tieBreakerName)
			throws SeatAllocationException {

		if (!tieBreakers.containsKey(tieBreakerName))
			throw new SeatAllocationException(
					"Tie breaker cannot be resolved: " + tieBreakerName);

		try {
			return ((Class<TieBreaker>) tieBreakers.get(tieBreakerName))
					.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new SeatAllocationException(
					"Tie breaker resolved but cannot be instantiated: "
							+ e.getMessage());
		}
	}
}
