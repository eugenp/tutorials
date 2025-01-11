package com.baeldung.spring.convertmonoobject;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class UserService {

    public Mono<User> getUser(String userId) {
        return Mono.empty();
    }
}
