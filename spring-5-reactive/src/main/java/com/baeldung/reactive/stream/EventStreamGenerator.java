/**
 *
 */
package com.baeldung.reactive.stream;

import java.time.Duration;

import org.reactivestreams.Publisher;
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
    Publisher<Event> events() {
        return Flux.interval(Duration.ofSeconds(1))
            .map(Event::of);
    }
}
