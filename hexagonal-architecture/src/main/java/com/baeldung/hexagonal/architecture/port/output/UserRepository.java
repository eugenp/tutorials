package com.baeldung.hexagonal.architecture.port.output;

import java.util.Optional;

import com.baeldung.hexagonal.architecture.core.domain.User;

public interface UserRepository {

    User createUser(User user);

    Optional<User> findUserById(Long id);

}
