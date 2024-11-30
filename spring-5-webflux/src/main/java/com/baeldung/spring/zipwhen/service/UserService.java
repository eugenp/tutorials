package com.baeldung.spring.zipwhen.service;

import com.baeldung.spring.zipwhen.model.User;

import reactor.core.publisher.Mono;

public class UserService {
    public Mono<User> getUser(String userId) {
        return Mono.just(new User(userId, "john Major"));
    }
}

