package com.baeldung.javalin.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class UserDao {

    private final List<User> users = Arrays.asList(
            new User(0, "Steve Rogers"),
            new User(1, "Tony Stark"),
            new User(2, "Carol Danvers")
    );

    private static UserDao userDao = null;

    private UserDao() {
    }

    static UserDao instance() {
        if (userDao == null) {
            userDao = new UserDao();
        }
        return userDao;
    }

    Optional<User> getUserById(int id) { return users.stream().filter(u -> u.id == id).findFirst(); }

    Iterable<String> getAllUsernames() {
        return users.stream().map(user -> user.name).collect(Collectors.toList());
    }
}
