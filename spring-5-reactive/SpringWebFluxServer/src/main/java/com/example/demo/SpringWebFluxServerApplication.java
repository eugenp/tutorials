package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringWebFluxServerApplication {

	Logger logger = LoggerFactory.getLogger(SpringWebFluxServerApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(SpringWebFluxServerApplication.class, args);
	}

}
