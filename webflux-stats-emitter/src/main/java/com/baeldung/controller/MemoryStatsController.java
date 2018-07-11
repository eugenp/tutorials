package com.baeldung.controller;

import com.baeldung.data.MemoryStats;
import com.baeldung.service.EventsEmitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

/**
 * Memory Stats Controller.
 */
@Controller
public class MemoryStatsController {

    @Autowired private EventsEmitter eventsEmitter;

    @GetMapping(value = "/api/memoryStats", produces = "application/stream+json")
    @ResponseBody
    public Flux<MemoryStats> emitEvents() {
        return eventsEmitter.emitMemoryStats();
    }
}
