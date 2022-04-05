package com.baeldung.httpfirewall.utility;

import com.baeldung.httpfirewall.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserTestUtility {
    public static User createUser() {
        return new User(UUID.randomUUID().toString(),"jhondoe", "jhondoe@gmail.com");
    }

    public static User createUserWithoutId() {
        return new User("","jhondoe", "jhondoe@gmail.com");
    }

    public static Optional<User> createUserWithId(String id) {
        // @formatter:off
        return Optional.of(new User(id, "jhondoe", "jhon.doe@gmail.com"));
        // @formatter:on
    }

    public static Optional<List<User>> createUsers() {
        // @formatter:off
        return Optional.of(Arrays.asList(
          new User(UUID.randomUUID().toString(), "jhondoe","jhon.doe@gmail.com" ),
          new User(UUID.randomUUID().toString(), "janedoe","jane.doe@gmail.com" ))
        );
        // @formatter:on
    }
}
