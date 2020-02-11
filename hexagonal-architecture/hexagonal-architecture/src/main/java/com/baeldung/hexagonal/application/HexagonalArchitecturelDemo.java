package com.baeldung.hexagonal.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.baeldung.hexagonal")
public class HexagonalArchitecturelDemo {
	public static void main(String[] args) {
		SpringApplication.run(HexagonalArchitecturelDemo.class, args);
	}
}
