package com.baeldung.serviceinjection;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceB {

    private Repository<User> repository;

    @Autowired
    public void setRepository(Repository<User> repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        return this.repository.findAll();
    }
}
