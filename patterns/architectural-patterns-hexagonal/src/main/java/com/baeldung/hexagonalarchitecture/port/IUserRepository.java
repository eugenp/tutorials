package com.baeldung.hexagonalarchitecture.port;

import com.baeldung.hexagonalarchitecture.core.User;

public interface IUserRepository {
    User getUserByUsername(String username);

    boolean addUser(User user);
}
