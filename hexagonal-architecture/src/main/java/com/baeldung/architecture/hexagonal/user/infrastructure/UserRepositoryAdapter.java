package com.baeldung.architecture.hexagonal.user.infrastructure;

import com.baeldung.architecture.hexagonal.user.core.model.User;
import com.baeldung.architecture.hexagonal.user.core.ports.outgoing.UserRepository;

public class UserRepositoryAdapter implements UserRepository {

    private final UserDatabase database;

    public UserRepositoryAdapter() {
        this.database = UserDatabase.database();
    }

    @Override
    public boolean save(User user) {
        return database.add(user);
    }

    @Override
    public boolean delete(String userId) {
        User user = database.findById(userId);
        return database.delete(user);
    }
}
