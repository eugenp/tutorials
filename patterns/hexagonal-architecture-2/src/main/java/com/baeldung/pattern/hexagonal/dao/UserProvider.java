package com.baeldung.pattern.hexagonal.dao;

import java.util.Optional;

public interface UserProvider {
    Optional<User> getUser(String login);
}
