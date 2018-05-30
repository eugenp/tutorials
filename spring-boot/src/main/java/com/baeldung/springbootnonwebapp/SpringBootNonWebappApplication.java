package com.baeldung.springbootnonwebapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootNonWebappApplication {

	private static final Logger LOG = LoggerFactory.getLogger(SpringBootNonWebappApplication.class);

	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");
		SpringApplication app = new SpringApplication(SpringBootNonWebappApplication.class);
		// This line of code, disables the web app setting
		app.setWebEnvironment(false);
		app.run(args);
		LOG.info("APPLICATION STARTED");
	}
}
