package com.baeldung.port;

import java.util.List;
import java.util.UUID;

import com.baeldung.domain.entity.user.User;
import com.baeldung.domain.exceptions.UserNotFoundException;

public interface UserRepository {

    User createUser(User user);

    boolean deleteUser(User user) throws UserNotFoundException;

    User findUserById(UUID userId) throws UserNotFoundException;

    List<User> listUsers();
}
