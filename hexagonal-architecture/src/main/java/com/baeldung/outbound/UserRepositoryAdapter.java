package com.baeldung.outbound;

import com.baeldung.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserRepositoryAdapter implements UserRepository {
    Map<String, User> userMap = new HashMap<>();

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public User createUser(User user) {
        userMap.put(user.userName, user);
        return user;
    }
}
