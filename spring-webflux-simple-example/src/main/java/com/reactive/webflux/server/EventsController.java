package com.reactive.webflux.server;

import java.time.Duration;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class EventsController {

    protected int eventNumber = 0;

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getEvent() {
        return Flux.fromStream(Stream.generate(() ->
          getNextEvent())).delayElements(Duration.ofSeconds(1));
    }

    public String getNextEvent() {
        return "Event: " + eventNumber++;
    }

    public int getEventNumber() {
        return eventNumber;
    }

    public void setEventNumber(int eventNumber) {
        this.eventNumber = eventNumber;
    }
}
