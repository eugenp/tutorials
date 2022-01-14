package com.baeldung.hexagonal.repository.impl;

import com.baeldung.hexagonal.domain.entities.User;
import com.baeldung.hexagonal.repository.UserRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("mongodb_userRepository")
public class MongoDbUserRepository implements UserRepository {

    private final MongoRepository<User, String> repository;

    public MongoDbUserRepository(MongoRepository<User, String> repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public void save(User user) {
        repository.save(user);
    }

    @Override
    public void update(User user) {
        repository.save(user);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

}
