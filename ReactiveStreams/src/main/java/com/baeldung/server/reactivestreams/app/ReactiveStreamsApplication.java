package com.baeldung.server.reactivestreams.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
@ComponentScan("com.baeldung.server.reactivestreams")
public class ReactiveStreamsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveStreamsApplication.class, args);
		
	}
	
}
