package com.baeldung.webflux.events.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;

public class EventsClient {

    
WebClient webClient = WebClient.builder()
        .baseUrl("http://localhost:8080")
        .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/stream+json")
        .build();    

    public Disposable streamEvents() {
        
        return webClient.get()
                .uri("/events")
                .exchange()
                .flatMapMany(clientResponse -> clientResponse.bodyToFlux(Long.class)) 
                .subscribe(s -> {
                    LOGGER.info("Event Timestamp: {}", s);
                });
    }
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EventsClient.class.getSimpleName());
}