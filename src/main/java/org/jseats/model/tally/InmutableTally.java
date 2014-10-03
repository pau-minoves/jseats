package org.jseats.model.tally;

import java.io.OutputStream;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.jseats.model.Candidate;

public interface InmutableTally {

	public void toXML(OutputStream out) throws JAXBException;

	public int getPotentialVotes();

	public int getEffectiveVotes();

	public Candidate getCandidateAt(int position);
	
	public int getNumberOfCandidates();
}
