package com.baeldung.reactive.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.reactive.components.EventsEmitterService;

import reactor.core.publisher.ConnectableFlux;

@RestController
public class EventsPublisherController {

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/events")
    @ResponseStatus(value = HttpStatus.OK)
    public ConnectableFlux<Long> getEvents() {
        return EventsEmitterService.getInstance();
    }

}
