package com.baeldung.reactive.evaluation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class ReactiveClient {

    private static final Logger logger = LoggerFactory.getLogger(ReactiveClient.class);

    public static void main(String[] args) throws InterruptedException {
        WebClient.create("http://127.0.0.1:8080")
            .get()
            .uri("/events")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .exchange()
            .flatMapMany(response -> response.bodyToFlux(Event.class))
            .subscribe(evt -> logger.info(evt.toString()));

        Thread.currentThread()
            .join();
    }
}
