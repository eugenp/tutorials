package com.baeldung.reactive.client;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class WebFluxClient {

    public Flux<String> getBookNames(WebClient webClient) {
        return webClient.get().exchange()
          .flatMapMany(bookName -> bookName.bodyToFlux(String.class));
    }

    public void logBookNames(Flux<String> bookNames) {
        bookNames.subscribe(System.out::println);
    }
}
