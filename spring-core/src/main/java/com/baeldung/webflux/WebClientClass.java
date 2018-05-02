package com.baeldung.webflux;

import java.net.URISyntaxException;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

public class WebClientClass {
	private WebClient client = WebClient.create("http://localhost:8080");

	public void getResult() throws URISyntaxException {
		
		Flux<String> result = client
			.get()
			.uri("/check")
			.accept(MediaType.TEXT_EVENT_STREAM)
			.retrieve()
			.bodyToFlux(String.class);
		
		result.subscribe(System.out::println);
	}
}
