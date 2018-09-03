package com.baeldung.reactive.webflux.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class SimpleWebfluxClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleWebfluxClient.class);

    public void connectAndReceiveEvents(String port) {
        if (port == null) {
            port = "8899";
        }
        WebClient client = WebClient.create("http://localhost:"+port);
        Flux<String> event$ = client.get()
            .uri("/events")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .retrieve()
            .bodyToFlux(String.class);
        event$.subscribe(event -> {
            LOGGER.info("Received " + event);
        }, error -> {
            LOGGER.error("Response Error", error);
        }, () -> {
            LOGGER.info("Stream complete");
        });
    }
}
