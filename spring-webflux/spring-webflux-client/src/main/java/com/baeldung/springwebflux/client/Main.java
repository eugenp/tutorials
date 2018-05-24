package com.baeldung.springwebflux.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.concurrent.TimeUnit;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.BodyExtractors.toFlux;

public class Main {
    public static void main(String... args) throws InterruptedException {
        Logger logger = LoggerFactory.getLogger(Main.class);

        Flux<Long> someFlux = WebClient
                .create("http://localhost:8080")
                .get()
                .uri("/fetch")
                .accept(TEXT_EVENT_STREAM)
                .exchange()
                .flatMapMany(response -> response.body(toFlux(Long.class)));
        someFlux.subscribe(l -> logger.info("Received data from server: {}", l));

        TimeUnit.MINUTES.sleep(10);
    }
}
