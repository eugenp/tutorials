package com.baeldung.reactive.realtime.controllers;

import com.baeldung.reactive.realtime.model.Event;
import com.baeldung.reactive.realtime.service.RealtimeEventGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/realtime-event")
public class RealtimeEventFlowController {

    @Autowired
    private RealtimeEventGeneratorService realtimeEventGeneratorService;

    @GetMapping(value = "/flow", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Event> flow() {
        return realtimeEventGeneratorService.flow();
    }

}
