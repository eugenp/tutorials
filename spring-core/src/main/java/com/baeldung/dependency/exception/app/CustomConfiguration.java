package com.baeldung.dependency.exception.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.baeldung.dependency.exception.repository"})
public class CustomConfiguration {
	public static void main(String[] args) {
		SpringApplication.run(CustomConfiguration.class, args);
	}
	@Bean
	public PurchaseDeptService service() {
		return new PurchaseDeptService();
	}
}
