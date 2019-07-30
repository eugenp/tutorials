package com.baeldung.hexagonalarchitecture.repository;

import com.baeldung.hexagonalarchitecture.model.User;

public interface UserRepositoryPort {

    void create(User user);

    User getUser(Long id);
}
