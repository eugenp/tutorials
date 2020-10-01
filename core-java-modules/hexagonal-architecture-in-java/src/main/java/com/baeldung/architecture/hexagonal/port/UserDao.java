package com.baeldung.architecture.hexagonal.port;

import com.baeldung.architecture.hexagonal.domain.user.User;

public interface UserDao {

    void createUser(User user);
}
