package com.baeldung.micronaut.helloworld.server.controller;

import com.baeldung.micronaut.helloworld.server.service.GreetingService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import io.reactivex.rxjava3.core.Single;
import jakarta.inject.Inject;

@Controller("/async/greet")
public class AsyncGreetController {

    @Inject
    private GreetingService greetingService;

    @Get("/{name}")
    public Single<String> greet(String name) {
        return Single.just(greetingService.getGreeting() + name);
    }
}
