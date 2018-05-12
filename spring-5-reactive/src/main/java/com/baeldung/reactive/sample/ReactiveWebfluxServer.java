package com.baeldung.reactive.sample;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
public class ReactiveWebfluxServer {

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/events")
    Flux<String> getEventStream() {
        Flux<String> eventFlux = Flux.fromStream(Stream.generate(() -> getCurrentTimeStamp()));
        Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));
        return Flux.zip(eventFlux, durationFlux)
            .map(Tuple2::getT1);

    }

    private String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").format(new Date());
    }

}
