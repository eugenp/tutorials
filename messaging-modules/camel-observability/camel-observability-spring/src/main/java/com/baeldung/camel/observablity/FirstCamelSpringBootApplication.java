package com.baeldung.camel.observablity;

import org.apache.camel.observation.starter.CamelObservation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@CamelObservation
@SpringBootApplication
public class FirstCamelSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstCamelSpringBootApplication.class, args);
	}

	@Bean
	SimpleProcessor simpleProcessor() {
		return new SimpleProcessor();
	}

	@Bean
	SimpleRouteBuilder simpleRouteBuilder(SimpleProcessor simpleProcessor) {
		return new SimpleRouteBuilder(simpleProcessor);
	}

}
