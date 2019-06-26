package com.baeldung.hexagonalArchitecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class HexagonalArchitectureDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HexagonalArchitectureDemoApplication.class, args);
	}

}
