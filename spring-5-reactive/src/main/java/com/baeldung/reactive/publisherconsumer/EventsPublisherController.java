package com.baeldung.reactive.publisherconsumer;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
public class EventsPublisherController {

    @GetMapping(value = "/uuids", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getUuids() {
        Flux<String> uuidFlux = Flux.fromStream(Stream.generate(() -> UUID.randomUUID().toString()));
        Flux<Long> emitterFlux = Flux.interval(Duration.ofSeconds(1));

        return Flux.zip(uuidFlux, emitterFlux).map(Tuple2::getT1);
    }
}
