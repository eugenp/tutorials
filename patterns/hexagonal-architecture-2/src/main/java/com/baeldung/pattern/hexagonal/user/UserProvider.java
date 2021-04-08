package com.baeldung.pattern.hexagonal.user;

public interface UserProvider {
    User getUser(String login);
}
