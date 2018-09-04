package com.baeldung.reactive.controller;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;

@RestController
public class RealTimeEventController {

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> getEvents() {

        return Flux.interval(Duration.ofSeconds(1))
            .map(id -> ServerSentEvent.<String> builder()
                .id(String.valueOf(id))
                .event("event")
                .data("Event Data :" + LocalTime.now()
                    .toString())
                .build());

    }
}
