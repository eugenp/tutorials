package com.baeldung.tutorial.hexagonal.adapter;

import com.baeldung.tutorial.hexagonal.core.User;
import com.baeldung.tutorial.hexagonal.port.out.UserRepository;

/**
 * No SQL implementation of Repository.
 * Example, No Implementation
 */
public class NoSQLUserRepositoryImpl implements UserRepository {
    
    public void saveUser(User user) {

    }
    
    public User getUser(String userEmail) {
        return null;
    }
    
    public boolean deleteUser(String userEmail) {
        return false;
    }
}
