package org.jseats.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class XMLProperty {

	@XmlAttribute
	public String key;
	@XmlValue
	public String value;

	public XMLProperty() {

	}

	public XMLProperty(String key, String value) {
		this.key = key;
		this.value = value;
	}

}
