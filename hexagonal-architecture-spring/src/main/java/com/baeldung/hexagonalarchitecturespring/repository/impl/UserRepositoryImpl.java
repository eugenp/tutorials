package com.baeldung.hexagonalarchitecturespring.repository.impl;

import com.baeldung.hexagonalarchitecturespring.domain.User;
import com.baeldung.hexagonalarchitecturespring.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final Map<UUID, User> users = new HashMap<>(0);

    @Override
    public Optional<User> findById(UUID id) {
        User user = users.get(id);
        return Optional.of(user);
    }

    @Override
    public User create(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void save(User user) {
        UUID id = user.getId();
        User persistedUser = users.get(id);
        if (persistedUser != null) {
            users.remove(id);
            users.put(user.getId(), user);
        }
    }
}
