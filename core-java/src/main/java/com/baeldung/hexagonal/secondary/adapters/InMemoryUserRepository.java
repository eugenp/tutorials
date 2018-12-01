package com.baeldung.hexagonal.secondary.adapters;

import java.util.HashMap;
import java.util.Map;

import com.baeldung.hexagonal.data.User;
import com.baeldung.hexagonal.secondary.ports.UserRepository;

public class InMemoryUserRepository implements UserRepository {

    Map<String, User> userMap = new HashMap<String, User>();

    @Override
    public boolean create(User user) {
        if (userMap.containsKey(user.getUid() + "pass:" + (user.getPassword()))) {
            return false;
        } else {

            userMap.put(user.getUid() + "pass:" + (user.getPassword()), user);
            return true;
        }
    }

    @Override
    public boolean fetch(String uid, String password) {
        if (userMap.containsKey(uid + "pass:" + password)) {
            return true;
        } else {
            return false;
        }

    }
}