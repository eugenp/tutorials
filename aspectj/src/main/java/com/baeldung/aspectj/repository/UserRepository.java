package com.baeldung.aspectj.repository;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    public void createUser(String name, int age) {
        System.out.println("User: " + name + ", age:" + age + " is created.");
    }

    public void deleteUser(String name) {
        System.out.println("User: " + name + " is deleted.");
    }
}