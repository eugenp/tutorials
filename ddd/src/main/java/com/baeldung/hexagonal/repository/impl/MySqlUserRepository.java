package com.baeldung.hexagonal.repository.impl;

import com.baeldung.hexagonal.domain.entities.User;
import com.baeldung.hexagonal.repository.UserRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("mySql_userRepository")
public class MySqlUserRepository implements UserRepository {

    private final CrudRepository<User, String> userRepository;

    public MySqlUserRepository(CrudRepository<User, String> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }
}
