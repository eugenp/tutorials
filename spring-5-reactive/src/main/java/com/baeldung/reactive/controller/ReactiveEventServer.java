package com.baeldung.reactive.controller;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;


@RestController
@RequestMapping("/events")
public class ReactiveEventServer {

    @GetMapping(path = "/stream/" , produces=MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> eventStream() {
        return  Flux.interval(Duration.ofSeconds(1)).map(tick -> {return "Event:" + tick;});
    }
}