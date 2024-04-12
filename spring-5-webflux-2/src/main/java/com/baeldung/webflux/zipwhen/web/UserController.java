package com.baeldung.webflux.zipwhen.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.baeldung.webflux.zipwhen.model.User;
import com.baeldung.webflux.zipwhen.service.DatabaseService;
import com.baeldung.webflux.zipwhen.service.EmailService;
import com.baeldung.webflux.zipwhen.service.UserService;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;

public class UserController {
    private final UserService userService;
    private final EmailService emailService;
    private final DatabaseService databaseService;

    public UserController(UserService userService, EmailService emailService, DatabaseService databaseService) {
        this.userService = userService;
        this.emailService = emailService;
        this.databaseService = databaseService;
    }

    @GetMapping("/example/{userId}")
    public Mono<ResponseEntity<String>> combineAllDataFor(@PathVariable String userId) {
        Mono<User> userMono = userService.getUser(userId);
        Mono<Boolean> emailSentMono = emailService.sendEmail(userId)
          .subscribeOn(Schedulers.parallel());
        Mono<String> databaseResultMono = userMono.flatMap(user -> databaseService.saveUserData(user)
          .map(Object::toString));

        return userMono.zipWhen(user -> emailSentMono, Tuples::of)
          .zipWhen(tuple -> databaseResultMono, (tuple, databaseResult) -> {
              User user = tuple.getT1();
              Boolean emailSent = tuple.getT2();
              return ResponseEntity.ok()
                .body("Response: " + user + ", Email Sent: " + emailSent + ", Database Result: " + databaseResult);
          });
    }
}
