package com.baeldung.aspectj.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public void createUser(String name, int age) {
        System.out.println("User created: " + name + " | age: " + age);
    }

    public void deleteUser(String name) {
        System.out.println("User deleted: " + name);
    }
}