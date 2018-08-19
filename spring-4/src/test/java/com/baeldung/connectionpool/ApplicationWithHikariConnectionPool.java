package com.baeldung.connectionpool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationWithHikariConnectionPool {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationWithHikariConnectionPool.class, args);
	}
}
