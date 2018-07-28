package com.baeldung.spring5webflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.stream.Stream;

@Slf4j
public class LocationEventHandler {
    private LocationEventService eventService;

    public LocationEventHandler(LocationEventService eventService) {
        this.eventService = eventService;
    }

    public Mono<ServerResponse> locationEvents(ServerRequest request) {
        log.info("locationEvents:request:{}", request.headers());
        Flux<LocationEvent> flux = Flux.fromStream(Stream.generate(() -> eventService.produceEvent())).delayElements(Duration.ofSeconds(1));
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(flux, LocationEvent.class);
    }
}
