package com.baeldung.reactive.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class EventHandler {
    private Logger log = LoggerFactory.getLogger(EventHandler.class);

    Mono<ServerResponse> handle(ServerRequest request) {
        return request.bodyToMono(String.class)
                .doOnNext(s -> log.info(s))
                .then(ServerResponse.ok().build());
    }
}
