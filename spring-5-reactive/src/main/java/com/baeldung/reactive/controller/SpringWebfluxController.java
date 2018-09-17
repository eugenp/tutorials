package com.baeldung.reactive.controller;

import com.baeldung.reactive.model.Event;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("/api")
public class SpringWebfluxController {

    @GetMapping(value = "/events", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Event> getAllEvents() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(Event::new);
    }
}