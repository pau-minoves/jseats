package org.jseats.model.result;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NullResultDecorator implements ResultDecorator {

	@Override
	public Result decorate(Result result) {
		return result;
	}

}
