package com.baeldung.springbootnonwebapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 1. Act as main class for spring boot application
 * 2. Also implements CommandLineRunner, so that code within run method
 * is executed before application startup but after all beans are effectively created
 * @author hemant
 *
 */
@SpringBootApplication
public class SpringBootConsoleApplication implements CommandLineRunner {

	private static Logger LOG = LoggerFactory.getLogger(SpringBootConsoleApplication.class);

	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");
		SpringApplication.run(SpringBootConsoleApplication.class, args);
		LOG.info("APPLICATION FINISHED");
	}

	/**
	 * This method will be executed after the application context is loaded and
	 * right before the Spring Application main method is completed.
	 */
	@Override
	public void run(String... args) throws Exception {
		LOG.info("EXECUTING : command line runner");
    		for (int i = 0; i < args.length; ++i) {
        		LOG.info("args[{}]: {}", i, args[i]);
    		}
	}
}
