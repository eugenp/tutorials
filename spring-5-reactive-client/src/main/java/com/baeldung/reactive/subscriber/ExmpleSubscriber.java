package com.baeldung.reactive.subscriber;

import org.springframework.web.reactive.function.client.WebClient;

public class ExampleSubscriber {
	
	private static final String SERVICE_BASE_URL = "";
	
	public void consumeWithWebClient() {
		WebClient.ResponseSpec responseSpec = WebClient
				.create(SERVICE_BASE_URL)
				.get()
				.uri("/helloFlux")
				.retrieve();
		responseSpec.bodyToFlux(Long.class).subscribe(System.out::println);
	}
}