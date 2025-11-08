package com.baeldung.hateoasvsswagger.repository;

import com.baeldung.hateoasvsswagger.model.NewUser;
import com.baeldung.hateoasvsswagger.model.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    private final Map<Integer, User> users = new HashMap<>();

    public UserRepository() {
        users.put(1, new User(1, "Baeldung", "baeldung@gmail.com", LocalDateTime.now()));
    }

    public List<User> getAllUsers() {
        return users.values().stream().map(u -> new User(u.getId(), u.getName(), u.getEmail(), LocalDateTime.now())).toList();
    }

    public User getUserById(int id) {
        return users.get(id);
    }

    public User createUser(NewUser user) {
        int id = users.size() + 1;
        return users.put(id, new User(id, user.name(), user.email(), LocalDateTime.now()));
    }
}
