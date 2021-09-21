package com.baeldung.hexagonal.domain.port;

import com.baeldung.hexagonal.domain.model.User;

import java.util.Optional;

public interface UserService {
    User addUser(User user);

    Optional<User> getUserById(long id);
}
