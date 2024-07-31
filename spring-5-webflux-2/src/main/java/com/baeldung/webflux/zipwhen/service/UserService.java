package com.baeldung.webflux.zipwhen.service;

import com.baeldung.webflux.zipwhen.model.User;

import reactor.core.publisher.Mono;

public class UserService {
    public Mono<User> getUser(String userId) {
        return Mono.just(new User(userId, "john Major"));
    }
}

