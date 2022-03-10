package com.baeldung.hexagonalarchitecture.domain.service;

import com.baeldung.hexagonalarchitecture.domain.model.User;

public interface UserService {
    String createUser(String username);

    void activateUser(User user);
}
