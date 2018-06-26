package com.baeldung.reactive.sse.controller;


import com.baeldung.reactive.sse.service.EventSubscriptionsService;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class EventController {

    private EventSubscriptionsService eventSubscriptionsService;

    public EventController(EventSubscriptionsService eventSubscriptionsService) {
        this.eventSubscriptionsService = eventSubscriptionsService;
    }

    @GetMapping(
            produces = MediaType.TEXT_EVENT_STREAM_VALUE,
            value = "/sse/events")
    public Flux<ServerSentEvent> events() {
        return eventSubscriptionsService.subscribe();
    }
}
