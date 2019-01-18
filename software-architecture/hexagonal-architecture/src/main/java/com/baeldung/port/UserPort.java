package com.baeldung.port;

import com.baeldung.User;

import java.util.Optional;

public interface UserPort {

    Optional<User> findBy(String username,
                          String password);
}
