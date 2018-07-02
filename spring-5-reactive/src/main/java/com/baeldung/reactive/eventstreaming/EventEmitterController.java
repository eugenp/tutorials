package com.baeldung.reactive.eventstreaming;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("/sse")
public class EventEmitterController {

    @GetMapping(value = "/randomEvent", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Event> randomEvent() {

        Flux<Event> eventFlux
          = Flux.fromStream(
            Stream.generate(() -> new Event(UUID.randomUUID().toString(),new Date())));

        Flux<Long> durationFlux
          = Flux.interval(Duration.ofSeconds(1));

        return Flux.zip(eventFlux, durationFlux).map(Tuple2::getT1);

    }
}
