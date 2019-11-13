package com.concertosoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.concertosoft")
public class NpciSimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(NpciSimulatorApplication.class, args);
	}

}
