package com.baeldung.hexagonal.architecture.core;

import com.baeldung.hexagonal.architecture.model.User;

public interface UserRepository {
    User loadUser(long id);

    void saveUser(User user);
}
