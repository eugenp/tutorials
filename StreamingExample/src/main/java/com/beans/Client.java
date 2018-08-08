package com.beans;

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
public class Client {

	Logger logger = LoggerFactory.getLogger(Client.class);

	@Bean
	WebClient createWebClient() {
		return WebClient.create("http://localhost:8080");
	}

	@Bean
	CommandLineRunner logger(WebClient webclient) {
		return args -> {
			webclient.get().uri("/streamOfEvents")
					.accept(MediaType.TEXT_EVENT_STREAM).exchange()
					.flatMapMany(clientRes -> clientRes.bodyToFlux(Event.class))
					.subscribe(System.out::println);
		};
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(Client.class).properties(
				Collections.singletonMap("server.port", "8081")).run(args);
	}

}
