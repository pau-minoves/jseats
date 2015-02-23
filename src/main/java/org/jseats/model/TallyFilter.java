package org.jseats.model;

public interface TallyFilter {

	String getName();

	Tally filter(Tally tally);
}
