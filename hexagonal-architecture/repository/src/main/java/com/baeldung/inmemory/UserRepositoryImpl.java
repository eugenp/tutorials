package com.baeldung.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.baeldung.domain.entity.user.User;
import com.baeldung.domain.exceptions.UserNotFoundException;
import com.baeldung.port.UserRepository;

public class UserRepositoryImpl implements UserRepository {
    private List<User> userDatabase = new ArrayList<>();

    public User createUser(User user) {
        userDatabase.add(user);

        return user;
    }

    public boolean deleteUser(User user) {
        return userDatabase.remove(user);
    }

    public User findUserById(UUID userId) throws UserNotFoundException {
        User user = userDatabase.stream().filter(u -> u.getId().equals(userId)).findAny().orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User was not found");
        }

        return user;
    }

    public List<User> listUsers() {
        return userDatabase;
    }
}
