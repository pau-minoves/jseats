package org.jseats.model.result;

public class NullResultDecorator implements ResultDecorator {

	@Override
	public Result decorate(Result result) {
		return result;
	}

}
