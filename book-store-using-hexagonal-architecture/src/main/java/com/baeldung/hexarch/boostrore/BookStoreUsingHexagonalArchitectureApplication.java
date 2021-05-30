package com.baeldung.hexarch.boostrore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class BookStoreUsingHexagonalArchitectureApplication {
	public static void main(String[] args) {
		SpringApplication.run(BookStoreUsingHexagonalArchitectureApplication.class, args);
	}
}
