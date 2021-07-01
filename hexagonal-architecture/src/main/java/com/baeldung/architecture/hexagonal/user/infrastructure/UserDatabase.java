package com.baeldung.architecture.hexagonal.user.infrastructure;

import com.baeldung.architecture.hexagonal.user.core.model.User;

import java.util.HashSet;
import java.util.Set;

public class UserDatabase {

    private static UserDatabase instance;

    private final Set<User> database = new HashSet<>();

    private UserDatabase() {
    }

    public static UserDatabase database() {
        if (instance == null) {
            instance = new UserDatabase();
        }
        return instance;
    }

    public boolean add(User user) {
        return this.database.add(user);
    }

    public boolean delete(User user) {
        return this.database.remove(user);
    }

    public User findById(String id) {
        return database.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}
