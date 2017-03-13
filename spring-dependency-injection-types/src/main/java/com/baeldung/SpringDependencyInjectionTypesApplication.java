package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath*:context.xml")
public class SpringDependencyInjectionTypesApplication {

	public static void main(String[] args) {
		SpringApplication
				.run(SpringDependencyInjectionTypesApplication.class, args);
	}
}
