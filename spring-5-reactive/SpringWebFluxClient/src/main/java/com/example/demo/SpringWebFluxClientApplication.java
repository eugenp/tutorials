package com.example.demo;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class SpringWebFluxClientApplication {

	Logger logger = LoggerFactory.getLogger(SpringWebFluxClientApplication.class);

	public static void main(String[] args) {
		new SpringApplicationBuilder(SpringWebFluxClientApplication.class)
				.properties(Collections.singletonMap("server.port", "8082")).run(args);

	}

	
	@Bean
	WebClient client() {
		return WebClient.create("http://localhost:8080");
	}

	@Bean
	CommandLineRunner demo(WebClient client) {
		return args -> {
			client.get().uri("/generateNumbers").accept(MediaType.TEXT_EVENT_STREAM).exchange()
					.flatMapMany(clientEvent -> clientEvent.bodyToFlux(Integer.class))
					.subscribe(msg -> logger.info(Integer.toString(msg)));
		};
	}
}
