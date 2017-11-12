package com.baeldung.serviceinjection;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceA {

    private final Repository<User> repository;

    @Autowired
    public UserServiceA(Repository<User> repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }
}
