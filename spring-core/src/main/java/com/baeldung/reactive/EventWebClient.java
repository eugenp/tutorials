package com.baeldung.reactive;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class EventWebClient {

    private WebClient webClient = WebClient.builder()
        .baseUrl("http://localhost:8080")
        .build();

    public void events() {
        webClient.get()
            .uri("/events")
            .accept(MediaType.APPLICATION_STREAM_JSON)
            .retrieve()
            .bodyToFlux(Event.class)
            .log()
            .subscribe(System.out::println);
    }

}
