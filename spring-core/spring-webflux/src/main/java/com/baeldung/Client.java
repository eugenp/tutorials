package com.baeldung;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.client.WebClient;

public class Client {
    public void connect() {
        WebClient.create("http://localhost:8080")
            .get()
            .uri("/hello")
            .retrieve()
            .bodyToFlux(ServerSentEvent.class)
            .filter(event -> event.event() != null)
            .subscribe(event -> System.out.println("Event: " + event));
    }
}
