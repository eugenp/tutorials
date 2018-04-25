package com.baeldung.reactive.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import com.baeldung.reactive.model.Event;

@Configuration
public class ReactiveEventClient {

	@Bean
	WebClient webClient() {
		return WebClient.create("http://localhost:8080/");
	}

	@Bean
	CommandLineRunner demo(WebClient webClient) {
		return args -> {
			webClient.get()
			.uri("/events")
			.exchange()
			.flatMapMany(res -> res.bodyToFlux(Event.class))
			.log()
			.subscribe(event -> System.out.println("event: " + event));
		};		
	}

}
