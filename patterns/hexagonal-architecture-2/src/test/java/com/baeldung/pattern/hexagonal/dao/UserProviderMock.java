package com.baeldung.pattern.hexagonal.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class UserProviderMock implements UserProvider {

    private List<User> users = new ArrayList<>();

    public UserProviderMock() {
        users.add(createUser("John", "Doe", "jdoe"));
        users.add(createUser("Andy", "Mock", "amock"));
        users.add(createUser("John", "Snow", "jsnow"));
    }

    @Override
    public Optional<User> getUser(String login) {
        return users.stream()
            .filter(u -> u.getLogin()
                .equals(login))
            .findAny();
    }

    private User createUser(String name, String surname, String login) {
        return User.builder()
            .name(name)
            .surname(surname)
            .login(login)
            .build();
    }
}
