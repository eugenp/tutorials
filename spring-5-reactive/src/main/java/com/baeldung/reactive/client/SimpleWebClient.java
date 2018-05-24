package com.baeldung.reactive.client;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

public class SimpleWebClient {

    WebClient client = WebClient.create("http://localhost:8080");

    public void consume() {

        Flux<Long> result = client.get()
            .uri("/streaming-data")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .retrieve()
            .bodyToFlux(Long.class);

        result.log()
            .subscribe();
    }
}
