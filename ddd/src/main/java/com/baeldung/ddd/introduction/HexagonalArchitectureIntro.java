package com.baeldung.ddd.introduction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.baeldung.ddd.introduction")
public class HexagonalArchitectureIntro {

	public static void main(String[] args) {
		SpringApplication.run(HexagonalArchitectureIntro.class, args);
	}
}
