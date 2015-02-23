package org.jseats.model;

public interface ResultDecorator {
	String getName();

	Result decorate(Result result);
}
