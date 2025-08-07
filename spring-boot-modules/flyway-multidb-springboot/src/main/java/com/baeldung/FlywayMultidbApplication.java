package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.baeldung")
public class FlywayMultidbApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlywayMultidbApplication.class, args);
	}

}
