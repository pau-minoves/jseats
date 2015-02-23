package org.jseats.xml;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "properties")
class XmlProperties {
	@XmlElement(name = "entry")
	public Collection<XMLProperty> entries = new ArrayList<XMLProperty>();
}