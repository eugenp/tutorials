package com.baeldung.hexagonalarchitecture.website.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.baeldung.hexagonalarchitecture.website")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}