package com.baeldung.hexagonal.domain.port;

import java.util.Optional;

import com.baeldung.hexagonal.domain.model.User;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(long id);
}
