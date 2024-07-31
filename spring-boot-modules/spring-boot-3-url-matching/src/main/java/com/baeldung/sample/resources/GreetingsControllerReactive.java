package com.baeldung.sample.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class GreetingsControllerReactive {

    @GetMapping("/some/reactive/greeting")
    public Mono<String> greeting() {
        return Mono.just("Hello reactive");
    }

    @GetMapping("/some/reactive/greeting/")
    public Mono<String> greetingTrailingSlash() {
        return Mono.just("Hello with slash reactive");
    }
}
