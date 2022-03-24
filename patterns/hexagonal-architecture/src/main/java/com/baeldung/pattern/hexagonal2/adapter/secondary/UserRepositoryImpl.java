package com.baeldung.pattern.hexagonal2.adapter.secondary;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.baeldung.pattern.hexagonal2.actor.secondary.Database;
import com.baeldung.pattern.hexagonal2.domain.model.User;
import com.baeldung.pattern.hexagonal2.port.secondary.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private Database database;

    public UserRepositoryImpl(Database database) {
        this.database = database;
    }

    @Override
    public User createUser(User user) {
        return this.database.addDataRecord(user);
    }

    @Override
    public List<User> getUsers() {
        return this.database.getDataRecords();
    }

    @Override
    public User deleteUser(UUID id) {
        return this.database.deleteDataRecord(id);
    }
}
