package org.jseats.model.result;

import javax.xml.bind.annotation.XmlRootElement;

import org.jseats.model.Result;
import org.jseats.model.ResultDecorator;

@XmlRootElement
public class NullResultDecorator implements ResultDecorator {

	@Override
	public Result decorate(Result result) {
		return result;
	}

}
