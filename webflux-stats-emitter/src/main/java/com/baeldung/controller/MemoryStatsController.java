package com.baeldung.controller;

import com.baeldung.data.MemoryStats;
import com.baeldung.service.EventsEmitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Memory Stats Controller.
 */
@Controller
public class MemoryStatsController {

    @Autowired private EventsEmitter eventsEmitter;

    /**
     * MemoryStats API to send stream of Memory Status once in 1 seconds.
     * @return
     */
    @GetMapping(value = "/api/memoryStats", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @ResponseBody
    public Flux<MemoryStats> emitEvents() {
        return eventsEmitter.emitMemoryStats();
    }
}
