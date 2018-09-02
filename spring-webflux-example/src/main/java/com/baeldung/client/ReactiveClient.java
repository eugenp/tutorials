package com.baeldung.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.model.Event;

import reactor.core.publisher.Flux;

@SpringBootApplication(scanBasePackages = "com.baeldung.controller")
public class ReactiveClient {

    private static Logger logger = LoggerFactory.getLogger(ReactiveClient.class);

    public static void main(String[] args) {
        new SpringApplicationBuilder(ReactiveClient.class).run(args);

        WebClient client = WebClient.create("http://localhost:8081");
        Flux<Event> eventFlux = client.get()
            .uri("/events")
            .retrieve()
            .bodyToFlux(Event.class);
        eventFlux.subscribe(n -> logger.info("Next eventId: {}, eventDate: {}", n.getId(), n.getDate()));
    }
}
