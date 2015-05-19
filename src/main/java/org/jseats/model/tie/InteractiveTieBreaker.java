package org.jseats.model.tie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.jseats.model.Candidate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This TieBreaker implementation will interrogate the user via the provided
 * in/out objects in order to resolve the tie.
 */
public class InteractiveTieBreaker extends BaseTieBreaker {

	Logger out;
	BufferedReader in;

	/**
	 * This constructor will default to System.in and whatever Logger object the
	 * slf4j provider has configured for this class
	 * (org.jseats.model.tie.InteractiveTieBreaker);
	 */
	public InteractiveTieBreaker() {
		this.in = new BufferedReader(new InputStreamReader(System.in));
		this.out = LoggerFactory.getLogger(InteractiveTieBreaker.class);
	}

	/**
	 * Provide the InputStream and Logger instances to use to interact with the
	 * user.
	 * 
	 * @param in
	 *            the InputStream to read selections from.
	 * @param out
	 *            the Logger object to print options to.
	 */
	public InteractiveTieBreaker(InputStream in, Logger out) {
		this.in = new BufferedReader(new InputStreamReader(in));
		this.out = out;
	}

	/**
	 * Provide the InputStream object to use read selections from.
	 * 
	 * @param in
	 *            the InputStream to read selections from.
	 */
	public void setIn(InputStream in) {
		this.in = new BufferedReader(new InputStreamReader(in));
	}

	/**
	 * Provide the Logger object to use to print options to.
	 * 
	 * @param out
	 *            the Logger object to print options to.
	 */
	public void setOut(Logger out) {
		this.out = out;
	}

	@Override
	public String getName() {
		return "console-tie-breaker";
	}

	@Override
	public Candidate innerBreakTie(List<Candidate> candidates) {

		out.debug("Candidates:");
		for (int i = 0; i < candidates.size(); i++) {
			out.debug("\t[" + i + "] " + candidates.get(i).toString());
		}

		int c = -2;
		do {
			try {
				out.debug("Select candidate index (-1 for none):");
				c = Integer.parseInt(in.readLine());

			} catch (NumberFormatException e) {
				continue;
			} catch (IOException e) {
				break;
			}
		} while (c < -1 || c >= candidates.size());

		if (c == -1)
			return null;
		else
			return candidates.get(c);
	}
}
