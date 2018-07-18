package com.infinite.webflux.stream;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class StreamService {

    public Flux<StreamEvent> infiniteEventStream() {
        return Flux
                .interval(Duration.ofSeconds(1))
                .map(second -> new StreamEvent(second.toString(), LocalDateTime.now()));
    }

}
