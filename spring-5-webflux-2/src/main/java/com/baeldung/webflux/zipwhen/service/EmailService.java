package com.baeldung.webflux.zipwhen.service;

import reactor.core.publisher.Mono;

public class EmailService {
    private final UserService userService;

    public EmailService(UserService userService) {
        this.userService = userService;
    }

    public Mono<Boolean> sendEmail(String userId) {
        return userService.getUser(userId)
          .flatMap(user -> {
              System.out.println("Sending email to: " + user.getEmail());
              return Mono.just(true);
          })
          .defaultIfEmpty(false);
    }
}
