package org.jseats.model;

import java.io.OutputStream;

import javax.xml.bind.JAXBException;

public interface InmutableTally {

	public void toXML(OutputStream out) throws JAXBException;

	public int getPotentialVotes();

	public int getEffectiveVotes();

	public Candidate getCandidateAt(int position);

	public int getNumberOfCandidates();

	int getCandidateIndex(Candidate candidate);
}
