package com.baeldung.webflux.zipwhen.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.baeldung.webflux.zipwhen.model.User;

import reactor.core.publisher.Mono;

public class DatabaseService {
    private Map<String, User> dataStore = new ConcurrentHashMap<>();

    //        public Mono<Boolean> saveUserData(User user) {
    //
    //
    //            return Mono.fromRunnable(() -> {
    //                // Simulate saving the user data to a database or data store
    //                dataStore.put(user.getId(), user);
    //
    //
    //            });
    //        }
    //}

    public Mono<Boolean> saveUserData(User user) {
        return Mono.create(sink -> {
            // Simulate saving the user data to a database or data store
            try {
                dataStore.put(user.getId(), user);
                sink.success(true); // Database save operation successful
            } catch (Exception e) {
                sink.success(false); // Database save operation failed
            }
        });
    }
}

