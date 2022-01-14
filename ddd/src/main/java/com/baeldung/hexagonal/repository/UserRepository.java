package com.baeldung.hexagonal.repository;

import com.baeldung.hexagonal.domain.entities.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(String id);
    void save(User user);
    void update(User user);
    void delete(User user);
}
