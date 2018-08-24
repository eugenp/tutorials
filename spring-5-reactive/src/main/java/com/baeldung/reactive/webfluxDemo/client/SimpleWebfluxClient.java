package com.baeldung.webfluxDemo.client;

import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class SimpleWebfluxClient {
    public static void main(String[] args) throws InterruptedException {
        WebClient webClient = WebClient.create("http://localhost:8080");
        // Mono<String> result =
        // webClient.get().retrieve().bodyToMono(String.class);
        Flux<String> result = webClient.get()
            .retrieve()
            .bodyToFlux(String.class);
    }

}
