package com.baeldung.datasourcedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class DataSourceIssueSolutionApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataSourceIssueSolutionApplication.class, args);
	}

}

