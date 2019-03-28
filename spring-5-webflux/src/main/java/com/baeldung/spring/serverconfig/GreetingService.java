package com.baeldung.spring.serverconfig;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GreetingService {

    public Mono<String> greet(String name) {
        return Mono.just("Greeting " + name);
    }
}
