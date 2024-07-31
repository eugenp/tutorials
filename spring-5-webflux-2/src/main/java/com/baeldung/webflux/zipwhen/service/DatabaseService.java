package com.baeldung.webflux.zipwhen.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.baeldung.webflux.zipwhen.model.User;

import reactor.core.publisher.Mono;

public class DatabaseService {
    private Map<String, User> dataStore = new ConcurrentHashMap<>();

    public Mono<Boolean> saveUserData(User user) {
        return Mono.create(sink -> {
            try {
                dataStore.put(user.getId(), user);
                sink.success(true);
            } catch (Exception e) {
                sink.success(false);
            }
        });
    }
}

