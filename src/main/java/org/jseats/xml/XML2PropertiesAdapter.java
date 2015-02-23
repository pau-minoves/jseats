package org.jseats.xml;

import java.util.Properties;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XML2PropertiesAdapter extends
		XmlAdapter<XmlProperties, Properties> {

	@Override
	public XmlProperties marshal(Properties props) throws Exception {

		XmlProperties map = new XmlProperties();

		for (Object k : props.keySet())
			map.entries.add(new XMLProperty((String) k, props
					.getProperty((String) k)));

		return map;
	}

	@Override
	public Properties unmarshal(XmlProperties props) throws Exception {
		Properties properties = new Properties();

		for (XMLProperty p : props.entries)
			properties.setProperty(p.key, p.value);

		return properties;
	}
}
