package com.baeldung.reactive.webflux;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientConsumer {
	public static void main(String[] args) throws InterruptedException {
		WebClient.create("http://localhost:8080")
		.get().uri("/webflux").accept(MediaType.TEXT_EVENT_STREAM)
		.retrieve()
		.bodyToFlux(String.class)
		.log()
		.blockLast();//Subscribe to this Flux and block indefinitely until the upstream signals its last value or completes.
	}
}
