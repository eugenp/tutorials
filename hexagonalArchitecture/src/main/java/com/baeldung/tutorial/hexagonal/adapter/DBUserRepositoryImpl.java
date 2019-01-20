package com.baeldung.tutorial.hexagonal.adapter;

import com.baeldung.tutorial.hexagonal.core.User;
import com.baeldung.tutorial.hexagonal.port.out.UserRepository;

/**
 * Database implementation of Repository
 * Example, No Implementation
 */
public class DBUserRepositoryImpl implements UserRepository {
    
    public void saveUser(User user) {
        //Save user to DB
    }
    
    public User getUser(String userEmail) {
        //retrieve user from DB
        return null;
    }
    
    public boolean deleteUser(String userEmail) {
        //delete user from DB.
        return false;
    }
}
