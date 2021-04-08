package com.baeldung.pattern.hexagonal.user;

import org.springframework.context.annotation.Profile;

@Profile("prod")
public class UserProviderImpl implements UserProvider {
    @Override
    public User getUser(String login) {
        // TODO implement access to DB here
        return null;
    }
}
