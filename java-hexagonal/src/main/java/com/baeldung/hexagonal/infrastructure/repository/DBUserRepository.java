package com.baeldung.hexagonal.infrastructure.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.domain.entity.User;
import com.baeldung.hexagonal.domain.repository.UserRepository;

@Repository
@Profile("db")
public class DBUserRepository implements UserRepository {

    @Override
    public User readUser() {
        // Reads user from a database and returns, for simplicity returning mock user.
        return new User(1L, "Baeldung from Database");
    }

}
