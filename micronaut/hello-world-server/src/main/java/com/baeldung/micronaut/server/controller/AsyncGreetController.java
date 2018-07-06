package com.baeldung.micronaut.server.controller;

import com.baeldung.micronaut.server.service.GreetingService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import reactor.core.publisher.Mono;

import javax.inject.Inject;

@Controller("/async/greet")
public class AsyncGreetController {

    @Inject
    private GreetingService greetingService;

    @Get("/{name}")
    public Mono<String> greet(String name) {
        return Mono.just(greetingService.getGreeting() + name);
    }
}
