package com.baeldung.bootcustomfilters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

/**
 * Boot application
 * @author hemant
 *
 */
@Profile("filter")
@SpringBootApplication(scanBasePackages = "com.baeldung.bootcustomfilters.*")
public class SpringBootFiltersApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootFiltersApplication.class, args);
	}
}
