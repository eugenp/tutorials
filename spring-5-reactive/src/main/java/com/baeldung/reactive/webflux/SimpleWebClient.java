package com.baeldung.reactive.webflux;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.Disposable;
import reactor.core.scheduler.Schedulers;

public class SimpleWebClient {

	private WebClient webClient = WebClient.builder()
	        .baseUrl("http://localhost:8080")
	        .build();
	
	public Disposable eventGenerated() {
		return webClient.get()
                .uri("/events")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .publishOn(Schedulers.single())
                .flatMapMany(response -> response.bodyToFlux(Event.class))
                .delayElements(Duration.ofMillis(1000))
                .log()
                .subscribe();
    }
}
