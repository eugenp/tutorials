package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.baeldung.*")
public class SpringBeanInjectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBeanInjectionApplication.class, args);
	}
}
