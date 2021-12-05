package com.baeldung.hexagonalarchitecturespring.repository;


import com.baeldung.hexagonalarchitecturespring.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findById(UUID id);
    User create(User user);
    void save(User user);
}
