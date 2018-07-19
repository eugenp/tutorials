package com.baeldung.reactive.event.streaming;

import java.time.Duration;
import java.util.Date;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/events")
public class WebfluxEventController {

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<WebFluxEvent> getEventStream() {
        Flux<WebFluxEvent> eventFlux = 
            Flux.fromStream(Stream.generate(() -> new WebFluxEvent(new Random().nextLong(), new Date())));
        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(eventFlux, durationFlux).map(Tuple2::getT1);
    }
}
