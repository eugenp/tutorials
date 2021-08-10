package com.baeldung.pattern.hexagonal.repository;

import com.baeldung.pattern.hexagonal.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserRepositoryImpl implements UserRepository {

    private Map<String, User> userMap = new HashMap<>();

    public void save(User user) {
        userMap.put(user.getEmail(), user);
    }

    public User getByEmail(String email) {
        return userMap.get(email);
    }
}
