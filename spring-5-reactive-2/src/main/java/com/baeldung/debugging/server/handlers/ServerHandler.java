package com.baeldung.debugging.server.handlers;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.baeldung.debugging.server.model.Foo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ServerHandler {

    private static Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    public Mono<ServerResponse> useHandler(final ServerRequest request) {
        // there are chances that something goes wrong here...
        return ServerResponse.ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> {
                    logger.info("retrieving Foo. Sequence: {}", sequence);
                    if (ThreadLocalRandom.current()
                        .nextInt(0, 50) == 1) {
                        throw new RuntimeException("There was an error retrieving the Foo!");
                    }
                    return new Foo(sequence, "name" + sequence);

                }), Foo.class);
    }

    public Mono<ServerResponse> useHandlerFinite(final ServerRequest request) {
        return ServerResponse.ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(Flux.range(0, 50)
                .map(sequence -> {
                    return new Foo(new Long(sequence), "theFooNameNumber" + sequence);
                }), Foo.class);
    }
}
