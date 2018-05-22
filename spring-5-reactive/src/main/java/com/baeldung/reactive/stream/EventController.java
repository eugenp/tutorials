/**
 *
 */
package com.baeldung.reactive.stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

/**
 * An example how to expose event stream using Webflux classic controller annotation style.
 * @author goobar
 *
 */
@RestController
public class EventController {
    /**
     * Event stream URI.
     */
    public static final String EVENT_URI = "/controller/events";
    private final EventStreamGenerator generator;

    /**
     * @param generator event stream generator
     */
    public EventController(EventStreamGenerator generator) {
        super();
        this.generator = generator;
    }

    /**
     * @return the event stream
     */
    @GetMapping(path = EVENT_URI, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Event> eventStream() {
        return generator.events();
    }
}
