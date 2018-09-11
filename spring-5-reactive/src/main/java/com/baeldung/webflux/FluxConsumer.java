package com.baeldung.webflux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

public class FluxConsumer {
    private static Logger logger = LoggerFactory.getLogger(FluxConsumer.class);

    public void consumeFlux() {
        Flux<String> messageFlux = WebClient.create("http://localhost:8080")
            .get()
            .uri("/produce")
            .retrieve()
            .bodyToFlux(String.class);
        messageFlux.subscribe(result -> logger.info(result));
    }
}
