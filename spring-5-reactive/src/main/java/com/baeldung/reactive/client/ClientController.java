package com.baeldung.reactive.client;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Date;

@RestController
public class ClientController {

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<ClientEvent> events() {
        return Flux.interval(Duration.ofSeconds(1))
            .map(seq -> new ClientEvent(seq, new Date()));
    }

}
