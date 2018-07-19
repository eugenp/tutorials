package com.baeldung.reactive.event.streaming;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class WebfluxEventClient {
    
    public void createWebClient() {
        WebClient.create("http://localhost:8080")
            .get()
            .uri("/events")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .retrieve()
            .bodyToFlux(WebFluxEvent.class)
            .subscribe(event -> System.out.println(event.toString()));
    }

}
