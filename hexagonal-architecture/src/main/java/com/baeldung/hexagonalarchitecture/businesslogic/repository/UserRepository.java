package com.baeldung.hexagonalarchitecture.businesslogic.repository;

import com.baeldung.hexagonalarchitecture.businesslogic.dto.User;

import java.util.UUID;

public interface UserRepository {
    void save(User user);

    User findUser(UUID uuid);
}
