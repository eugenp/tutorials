package com.baeldung.outbound;

import com.baeldung.model.User;

import java.util.List;

public interface UserRepository {
    List<User> getAllUsers();

    User createUser(User user);
}
