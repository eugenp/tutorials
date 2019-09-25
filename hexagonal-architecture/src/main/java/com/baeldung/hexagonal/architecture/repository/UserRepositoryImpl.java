package com.baeldung.hexagonal.architecture.repository;

import com.baeldung.hexagonal.architecture.core.UserRepository;
import com.baeldung.hexagonal.architecture.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class UserRepositoryImpl implements UserRepository {
    private final Map<Long, User> users = new ConcurrentHashMap<>();

    @Override
    public User loadUser(long id) {
        return users.get(id);
    }

    @Override
    public void saveUser(User user) {
        users.put(user.getId(), user);
    }

    public void saveUsers(User... users) {
        Stream.of(users).forEach(this::saveUser);
    }
}
