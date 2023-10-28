package com.baeldung.ddd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.baeldung.ddd.order")
public class PersistingDddAggregatesApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersistingDddAggregatesApplication.class, args);
	}
}
