package com.baeldung.webflux.zipwhen.service;

import com.baeldung.webflux.zipwhen.model.User;

import reactor.core.publisher.Mono;

public class UserService {
    public Mono<User> getUser(String userId) {
        // Replace with your implementation to validate the user
        // and return a Mono<User> with the validated user data
        // For example:
        return Mono.just(new User(userId, "John Doe"));
    }
}

