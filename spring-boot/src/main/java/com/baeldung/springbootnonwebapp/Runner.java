package com.baeldung.springbootnonwebapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(Runner.class);

	/**
	 * This method will be executed after the application context is loaded and
	 * right before the Spring Application main method is completed.
	 */
	@Override
	public void run(String... args) throws Exception {
		LOG.info("START : command line runner");
		LOG.info("EXECUTING : command line runner");
		LOG.info("END   : command line runner");
	}
}
