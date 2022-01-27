package com.baeldung.hexagonal.architecture.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class HexagonalArchitectureSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(HexagonalArchitectureSampleApplication.class, args);

	}

}
