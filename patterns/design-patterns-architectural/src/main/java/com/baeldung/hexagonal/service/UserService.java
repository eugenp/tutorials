package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.domain.User;
import com.baeldung.hexagonal.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public User registerUser(User user);
    public User upgradeUser(User user) throws UserNotFoundException;
    public List<User> getAllUsrs();
    public Optional<User> findUserById(int userId);

}
