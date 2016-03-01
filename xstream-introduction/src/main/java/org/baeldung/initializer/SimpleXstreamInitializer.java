package org.baeldung.initializer;

import com.thoughtworks.xstream.XStream;

public class SimpleXstreamInitializer {

	private static XStream xtreamInstance;

	public static XStream getXstreamInstance() {
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