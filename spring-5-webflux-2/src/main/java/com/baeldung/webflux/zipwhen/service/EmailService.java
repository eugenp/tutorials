package com.baeldung.webflux.zipwhen.service;

import reactor.core.publisher.Mono;

//public class EmailService {
//    public Mono<Boolean> sendEmail(String userId) {
//      //code to send the email
//        return Mono.just(true);
//    }
//}


public class EmailService {
    private final UserService userService;

    public EmailService(UserService userService) {
        this.userService = userService;
    }

    public Mono<Boolean> sendEmail(String userId) {
        return userService.getUser(userId)
          .flatMap(user -> {
              // Code to send the email using the user's information
              System.out.println("Sending email to: " + user.getEmail());
              return Mono.just(true);
          })
          .defaultIfEmpty(false);
    }
}
