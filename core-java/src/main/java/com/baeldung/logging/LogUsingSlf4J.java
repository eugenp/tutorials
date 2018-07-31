package com.baeldung.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUsingSlf4J {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(LogUsingSlf4J.class);

		logger.error("An exception occurred!");
		logger.error("An exception occurred!", new Exception("Custom exception"));
		logger.error("{}, {}! An exception occurred!", "Hello", "World", new Exception("Custom exception"));
	}

}
