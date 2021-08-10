package com.baeldung.pattern.hexagonal.repository;

import com.baeldung.pattern.hexagonal.domain.model.User;

public interface UserRepository {

    void save(User user);

    User getByEmail(String email);
}
