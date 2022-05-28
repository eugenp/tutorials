package com.baeldung.reactive.security;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GreetService {

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<String> greet() {
        return Mono.just("Hello from service!");
    }

}
