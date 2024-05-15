package com.baeldung.optionaluses;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserRepositoryWithOptional {

    private final List<User> dbUsers = Arrays.asList(new User("1", "John"), new User("2", "Maria"), new User("3", "Daniel"));

    public Optional<User> findById(String id) {

        for (User u : dbUsers) {
            if (u.getId().equals(id)) {
                return Optional.of(u);
            }
        }

        return Optional.empty();
    }

    public void throwExceptionWhenUserIsPresent(String id) {

        this.findById(id)
            .ifPresent(user -> {
                throw new UserFoundException("User with ID : " + user.getId() + " is found");
            });

    }
}
