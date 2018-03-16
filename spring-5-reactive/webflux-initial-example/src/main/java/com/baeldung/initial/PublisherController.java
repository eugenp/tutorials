package com.baeldung.initial;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * Publisher Rest controller
 */
@RestController
public class PublisherController {

        /**
         * Create recursive events per second sending the "index" value
         */
        @GetMapping(path = "createEvent", produces = "text/event-stream")
        public Flux<Long> createEvent() {
                return Flux.interval(Duration.ofMillis(1000)).
                        map(index -> index);
        }
}
