package com.baeldung.springdispatcherservlet.services;

import org.springframework.stereotype.Service;

import com.baeldung.springdispatcherservlet.domain.User;

@Service
public class UserService {

    public User fetchUserByFirstName(String firstName) {
        return new User(1, firstName, "Everyperson");
    }

    public User exampleUser() {
        return new User(1, "Example", "Everyperson");
    }
}