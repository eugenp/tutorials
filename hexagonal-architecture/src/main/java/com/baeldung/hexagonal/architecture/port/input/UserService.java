package com.baeldung.hexagonal.architecture.port.input;

import java.util.Optional;

import com.baeldung.hexagonal.architecture.core.domain.User;

public interface UserService {

    User createUser(User user);

    Optional<User> findUserById(Long id);

}
