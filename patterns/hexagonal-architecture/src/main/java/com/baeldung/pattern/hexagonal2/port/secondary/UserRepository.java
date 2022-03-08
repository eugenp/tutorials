package com.baeldung.pattern.hexagonal2.port.secondary;

import java.util.List;
import java.util.UUID;

import com.baeldung.pattern.hexagonal2.domain.model.User;

public interface UserRepository {
    User createUser(User user);

    List<User> getUsers();

    User deleteUser(UUID id);
}
