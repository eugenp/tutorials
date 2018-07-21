package com.baeldung.webflux.client;

import org.springframework.web.reactive.function.client.WebClient;

public class WebFluxClient {

    public static void main(String[] args) throws InterruptedException {

	WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
	webClient.get().uri("/events").exchange().block().bodyToMono(String.class).block();

    }

}
