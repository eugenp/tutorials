package com.baeldung.springwebflux;

import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.reactive.function.*;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.*;

@Component
public class ExampleHandler {
    public Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromObject("Hello from webflux"));
    }
}
