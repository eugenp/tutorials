package com.baeldung.demo.eventstreamwebfluxdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class EventStreamController
{
    @RequestMapping("/events")
    public Flux<Long> getServerEvents()
    {
        return Flux.interval(Duration.ofMillis(1000));
    }
}
