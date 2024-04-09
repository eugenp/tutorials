package com.baeldung.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class TraceController {

    @GetMapping(value = "/trace-annotated")
    public Mono<String> trace(@RequestHeader(name = "traceId") final String traceId) {
        return Mono.just("TraceId: ".concat(traceId));
    }

    @GetMapping(value = "/trace-exceptional")
    public Mono<String> traceExceptional() {
        return Mono.just("Traced");
    }
}
