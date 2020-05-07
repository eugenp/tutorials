package com.baeldung.hexagonal.architecture.adapter.output.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.architecture.core.domain.User;
import com.baeldung.hexagonal.architecture.port.output.UserRepository;

@Repository
public class InMemoryRepositoryAdapter implements UserRepository {

    private Map<Long, User> inMemoryDataStore = new HashMap<>();

    @Override
    public User createUser(User user) {
        inMemoryDataStore.putIfAbsent(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findUserById(Long userId) {
        Optional<User> opt = Optional.empty();
        if (this.inMemoryDataStore.containsKey(userId)) {
            opt = Optional.of(inMemoryDataStore.get(userId));
        }
        return opt;
    }

}
