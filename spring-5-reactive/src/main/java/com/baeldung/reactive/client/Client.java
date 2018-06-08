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
        flux.subscribe(i -> onDataReceived(i), e -> onError(e));
    }

    /**
     * A mock for the function that receives integer numbers from the stream.
     * 
     * The current implementation just prints the received number.
    *  
     * @param number single stream's number 
     */
    private void onDataReceived(Long number) {
        System.out.println("Received number: " + number);
    }

    /**
     * A mock for the function that handles the errors. 
     * The current implementation just prints out the error message.
     * @param t
     */
    private void onError(Throwable t) {
        System.out.println(t.getMessage());
    }
}
