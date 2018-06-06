package com.baeldung.reactive.functional.handler;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class HeartBeatHandler {

    public Mono<ServerResponse> stillAlive(ServerRequest request) {
        return ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(beat(), String.class);
    }

    private Flux<String> beat() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(i -> DateTimeFormatter.ofPattern("HH:mm:ss")
                        .format(Instant.now().atZone(ZoneId.systemDefault())))
                .share();
    }
}