package com.baeldung.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class Spring5ReactiveClientApplication {

    private WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8080/")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_EVENT_STREAM_VALUE)
            .build();

    public static void main(String[] args) {
        SpringApplication.run(Spring5ReactiveClientApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void printFibonacciStreamValues() {
        webClient.get()
                .uri("/fibonacci")
                .exchange()
                .flatMapMany(clientResponse -> clientResponse.bodyToFlux(Long.class))
                .subscribe(System.out::println);
    }
}
