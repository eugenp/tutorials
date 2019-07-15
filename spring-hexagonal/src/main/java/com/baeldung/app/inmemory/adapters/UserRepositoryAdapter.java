package com.baeldung.app.inmemory.adapters;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.app.domain.User;
import com.baeldung.app.domain.ports.UserRepository;

public class UserRepositoryAdapter implements UserRepository {

    private List<User> users = new ArrayList<>();

    public UserRepositoryAdapter() {
        users.add(new User(1, "Henry"));
        users.add(new User(2, "Josh"));
    }

    @Override
    public List<User> getUsers() {
        return users;
    }
}
