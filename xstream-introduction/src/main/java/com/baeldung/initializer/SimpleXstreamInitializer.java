package com.baeldung.initializer;

import com.thoughtworks.xstream.XStream;

public class SimpleXstreamInitializer {

	private static XStream xstreamInstance;

	public static XStream getXstreamInstance() {
		if (xstreamInstance == null) {
			synchronized (SimpleXstreamInitializer.class) {
				if (xstreamInstance == null) {
					xstreamInstance = new XStream();
				}
			}
		}
		return xstreamInstance;
	}
}