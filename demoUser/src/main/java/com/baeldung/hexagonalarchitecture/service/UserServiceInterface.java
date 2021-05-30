package com.baeldung.hexagonalarchitecture.service;

import com.baeldung.hexagonalarchitecture.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {

    User addUser(User user);
    Optional<User> getUserById(long id);
    List<User> getAllUsers();
}
