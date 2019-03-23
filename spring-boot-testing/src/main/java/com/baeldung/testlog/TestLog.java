package com.baeldung.testlog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLog {

	Logger LOG = LoggerFactory.getLogger(this.getClass());

	public void info(String msg) {
		LOG.info(msg);
	}

	public void error(String msg) {
		LOG.error(msg);
	}

	public void trace(String msg) {
		LOG.trace(msg);
	}

}
