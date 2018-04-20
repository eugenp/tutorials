package com.webflux.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class WebfluxServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxServerApplication.class, args);
	}
}
