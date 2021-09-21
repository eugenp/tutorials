package com.baeldung.hexagonal.domain.port;

import java.util.Optional;

import com.baeldung.hexagonal.domain.model.User;

public interface UserService {
    User addUser(User user);

    Optional<User> getUserById(long id);
}
