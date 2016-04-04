package com.baeldung.initializer;

import com.thoughtworks.xstream.XStream;

public class SimpleXstreamInitializer {

	private XStream xtreamInstance;

	public XStream getXstreamInstance() {
		if (xtreamInstance == null) {
			synchronized (SimpleXstreamInitializer.class) {
				if (xtreamInstance == null) {
					xtreamInstance = new XStream();
				}
			}
		}
		return xtreamInstance;
	}
}