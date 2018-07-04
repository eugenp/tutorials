package com.baeldung.reactive.client.reactiveclient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class ReactiveClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveClientApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			WebClient.create("http://localhost:8080/")
					.get()
					.uri("infinite-event-stream")
					.exchange()
					.flatMapMany(clientResponse -> clientResponse.bodyToFlux(Long.class))
					.subscribe(System.out::println);
		};
	}
}
