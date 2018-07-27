package com.baeldung.reactive.webflux;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@RestController
public class EventController {

    Logger logger = LoggerFactory.getLogger(EventController.class);

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/events")
    Flux<ServerSentEvent<Event>> produceEvents() {
            Flux<ServerSentEvent<Event>> eventFlux = Flux
                            .fromStream(Stream
                                            .generate(
                                                            () -> new Event(System.currentTimeMillis(),
                                                                            new Date())).map(
                                                            data -> ServerSentEvent.<Event> builder()
                                                                            .event("Event").data(data).build()));
            Flux<Long> durationFlux = Flux.interval(Duration.ofSeconds(1));
            return Flux.zip(eventFlux, durationFlux).map(Tuple2::getT1);
    }

}
