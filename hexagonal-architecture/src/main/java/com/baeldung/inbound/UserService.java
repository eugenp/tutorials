package com.baeldung.inbound;

import com.baeldung.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User createUser(User user);
}
