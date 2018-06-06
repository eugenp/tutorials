package com.baeldung.reactive.client;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

/**
 * Client that consumes the stream produced by the reactive server 
 * at hardcoded url http://localhost:8080/stream.
 */
public class Client {

    /**
     * Infinite stream of integer numbers 
     */
    private Flux<Long> flux;

    public Client() {
        flux = WebClient.create("http://localhost:8080")
            .get()
            .uri("/stream")
            .accept(MediaType.APPLICATION_STREAM_JSON)
            .exchange()
            .flatMapMany(response -> response.bodyToFlux(Long.class));
    }

    /**
     * Subscribe to the stream of data
     */
    public void consume() {
        flux.subscribe(i -> store(i), e -> System.out.println(e.getMessage()));
    }

    /**
     * A mock for the function that stores integer numbers from the stream.
     * @param number single stream's number 
     */
    private void store(Long number) {
        System.out.println("Storing: " + number);
    }
}
