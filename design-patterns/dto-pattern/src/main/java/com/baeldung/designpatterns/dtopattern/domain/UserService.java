package com.baeldung.designpatterns.dtopattern.domain;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public void save(User user) {
        Objects.requireNonNull(user);
        repository.save(user);
    }
}
