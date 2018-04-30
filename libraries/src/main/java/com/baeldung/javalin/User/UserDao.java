package com.baeldung.javalin.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserDao {

    private final List<User> users = Arrays.asList(
            new User("Steve"),
            new User("Rogers"),
            new User("Captain America")
    );

    private static UserDao userDao = null;

    private UserDao() {
    }

    public static UserDao instance() {
        if (userDao == null) {
            userDao = new UserDao();
        }
        return userDao;
    }

    public User getUserByUsername(String name) {
        return users.stream().filter(u -> u.name.equals(name)).findFirst().orElse(null);
    }

    public Iterable<String> getAllUsernames() {
        return users.stream().map(user -> user.name).collect(Collectors.toList());
    }
}
