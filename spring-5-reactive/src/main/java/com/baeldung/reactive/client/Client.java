package com.baeldung.reactive.client;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class Client {
    public static void main(String[] args) {
        WebClient client = WebClient.builder()
            .baseUrl("http://localhost:8080")
            .build();

        Flux<ClientEvent> result = client.get()
            .uri("/events")
            .retrieve()
            .bodyToFlux(ClientEvent.class);

        result.subscribe(Client::handleResponse);
    }

    private static void handleResponse(ClientEvent clientEvent) {
        System.out.println("response " + clientEvent);
    }
}
