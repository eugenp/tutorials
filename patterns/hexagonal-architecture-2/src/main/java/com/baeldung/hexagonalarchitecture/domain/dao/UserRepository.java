package com.baeldung.hexagonalarchitecture.domain.dao;

import com.baeldung.hexagonalarchitecture.domain.model.User;

public interface UserRepository {
    String save(User user);

    User findById(String id);
}
