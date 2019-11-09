package com.baeldung.service;

import com.baeldung.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findOne(String username);
}
