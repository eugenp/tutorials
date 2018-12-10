package com.baeldung.port.service;

import com.baeldung.domain.User;

import java.util.Optional;

public interface UserServicePort {

    User createUser(String name, String username, String password, String email, String phone);

    Optional<User> getUser(Integer userId);
}
