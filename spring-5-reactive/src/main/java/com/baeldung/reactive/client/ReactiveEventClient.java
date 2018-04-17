package com.baeldung.reactive.client;

import org.springframework.web.reactive.function.client.WebClient;
import com.baeldung.reactive.model.Event;

public class ReactiveEventClient {
	
	public static void main(String[] args) {
		WebClient webClient = WebClient.create("http://localhost:8080/"); 
		webClient.get()
		.uri("/events")
		.exchange()
		.flatMapMany(res -> res.bodyToFlux(Event.class))
		.log()
		.subscribe(event -> System.out.println("event: " + event));
	}

}
