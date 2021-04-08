package com.baeldung.pattern.hexagonal.user;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public User getUser(String login) {
        return users.stream()
                .filter(u -> u.getLogin().equals(login))
                .findAny()
                .orElse(null);
    }

    private User createUser(String name, String surname, String login) {
        return User.builder()
                .name(name)
                .surname(surname)
                .login(login)
                .build();
    }
}
