package org.jseats.model.tie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import org.jseats.SeatAllocatorLauncher;
import org.jseats.model.Candidate;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class InteractiveTieBreaker extends BaseTieBreaker {

	Logger out;
	BufferedReader in;

	public InteractiveTieBreaker() {
		this.in = new BufferedReader(new InputStreamReader(System.in));
		this.out = LoggerFactory.getLogger(InteractiveTieBreaker.class);
	}

	public InteractiveTieBreaker(InputStream in, Logger out) {
		this.in = new BufferedReader(new InputStreamReader(in));
		this.out = out;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}

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
