package org.jseats.model;

@SuppressWarnings("serial")
public class SeatAllocationException extends Exception {

	public SeatAllocationException(String description) {
		super(description);
	}

	public SeatAllocationException(Throwable e) {
		super(e);
	}

	public SeatAllocationException(String description, Throwable e) {
		super(description, e);
	}
}
