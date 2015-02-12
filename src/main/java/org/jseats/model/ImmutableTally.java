package org.jseats.model;

import javax.xml.bind.JAXBException;
import java.io.OutputStream;

public interface ImmutableTally {

	public void toXML(OutputStream out) throws JAXBException;

	public int getPotentialVotes();

	public int getEffectiveVotes();

	public Candidate getCandidateAt(int position);

	public int getNumberOfCandidates();

	int getCandidateIndex(Candidate candidate);
}
