package com.baeldung.reactive.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/")
    public Mono<String> greet(Mono<Principal> principal) {
        return principal
          .map(Principal::getName)
          .map(name -> String.format("Hello, %s", name));
    }

    @GetMapping("/admin")
    public Mono<String> greetAdmin(Mono<Principal> principal) {
        return principal
          .map(Principal::getName)
          .map(name -> String.format("Admin access: %s", name));
    }

    @GetMapping("/greetingService")
    public Mono<String> greetingService() {
        return greetingService.greet();
    }

}
