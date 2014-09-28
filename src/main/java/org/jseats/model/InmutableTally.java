package org.jseats.model;

import java.io.OutputStream;
import java.util.List;

import javax.xml.bind.JAXBException;

public interface InmutableTally {

	void toXML(OutputStream out) throws JAXBException;

	int getPotentialVotes();

	int getEffectiveVotes();

	Candidate getCandidateAt(int position);
	
	int getNumerOfCandidates();
}
