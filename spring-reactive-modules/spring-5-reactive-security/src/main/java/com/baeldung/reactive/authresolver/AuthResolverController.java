package com.baeldung.reactive.authresolver;

import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AuthResolverController {

    @GetMapping("/customer/welcome")
    public Mono<String> sayWelcomeToCustomer(Mono<Principal> principal) {
        return principal
          .map(Principal::getName)
          .map(name -> String.format("Welcome to our site, %s!", name));
    }

    @GetMapping("/employee/welcome")
    public Mono<String> sayWelcomeToEmployee(Mono<Principal> principal) {
        return principal
          .map(Principal::getName)
          .map(name -> String.format("Welcome to our company, %s!", name));
    }

}
