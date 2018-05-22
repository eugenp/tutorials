/**
 *
 */
package com.baeldung.reactive.stream;

import java.time.Duration;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

/**
 * A strategy to generate events.
 * @author goobar
 *
 */
@Component
class EventStreamGenerator {
    /**
     * @return the events stream
     */
    Flux<Event> events() {
        return Flux.interval(Duration.ofSeconds(1))
            .map(Event::of);
    }
}
