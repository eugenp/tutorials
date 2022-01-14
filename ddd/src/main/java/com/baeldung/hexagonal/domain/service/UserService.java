package com.baeldung.hexagonal.domain.service;

import com.baeldung.hexagonal.domain.entities.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getUser(String id);

    String getEmail(String id);
}
