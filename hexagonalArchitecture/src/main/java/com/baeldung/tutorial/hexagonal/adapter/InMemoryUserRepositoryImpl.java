package com.baeldung.tutorial.hexagonal.adapter;

import com.baeldung.tutorial.hexagonal.core.User;
import com.baeldung.tutorial.hexagonal.port.out.UserRepository;

import java.util.HashMap;

/**
 * In Memory Implementation of Repository.
 */
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final HashMap<String, User> userRepo = new HashMap<>();

    public void saveUser(User user) {
        userRepo.put(user.getEmail(), user);
    }
    
    public User getUser(String userEmail) {
        return userRepo.get(userEmail);
    }
    
    public boolean deleteUser(String userEmail) {
        return userRepo.remove(userEmail) != null ? true : false;
    }

}
