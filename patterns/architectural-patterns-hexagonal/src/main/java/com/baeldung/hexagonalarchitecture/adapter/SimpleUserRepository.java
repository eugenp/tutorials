package com.baeldung.hexagonalarchitecture.adapter;

import java.util.HashSet;
import java.util.Set;

import com.baeldung.hexagonalarchitecture.core.User;
import com.baeldung.hexagonalarchitecture.port.IUserRepository;

public class SimpleUserRepository implements IUserRepository {

    private Set<User> userSet;

    public SimpleUserRepository() {
        userSet = new HashSet<>();
    }

    @Override
    public User getUserByUsername(String username) {
        User result = null;
        for (User user : userSet) {
            if (username.equals(user.getUsername())) {
                result = user;
                break;
            }
        }
        return result;
    }

    @Override
    public boolean addUser(User user) {
        return userSet.add(user);
    }
}
