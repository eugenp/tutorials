package com.baeldung.optionaluses;

import java.util.Arrays;
import java.util.List;

public class UserRepositoryWithNull {

    private final List<User> dbUsers = Arrays.asList(new User("1", "John"), new User("2", "Maria"), new User("3", "Daniel"));

    public User findById(String id) {

        for (User u : dbUsers) {
            if (u.getId().equals(id)) {
                return u;
            }
        }

        return null;
    }
}
