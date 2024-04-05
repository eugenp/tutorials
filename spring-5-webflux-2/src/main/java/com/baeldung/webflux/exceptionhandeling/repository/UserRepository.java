package com.baeldung.webflux.exceptionhandeling.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.baeldung.webflux.exceptionhandeling.model.User;

@Repository
public class UserRepository {
    private final Map<String, User> userDatabase = new ConcurrentHashMap<>();

    public UserRepository() {
        userDatabase.put("1", new User("1", "John Doe"));
        userDatabase.put("2", new User("2", "Jane Smith"));
    }

    public User findById(String id) {
        return userDatabase.get(id);
    }
}
