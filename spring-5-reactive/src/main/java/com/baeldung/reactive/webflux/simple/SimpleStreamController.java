package com.baeldung.reactive.webflux.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class SimpleStreamController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleStreamController.class);

    @GetMapping(value = "/events")
    public Flux<String> getEvents() {
        LOGGER.info("GET /events");
        return Flux.<String>create(emitter -> {
            new Thread(() -> {
                while (true) {
                    emitter.next("event");
                    LOGGER.info("event emitted");
                    try {Thread.sleep(1000);} catch (InterruptedException ex) {}
                }
            }).start();
        }).log();
    }
}
