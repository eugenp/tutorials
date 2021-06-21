package com.baeldung.ddd.hexagonal.arch.core.ports.driven;

import com.baeldung.ddd.hexagonal.arch.core.domain.User;

public interface UserRepository {
    void save(User user);
    void activate(User user);
    User findByEmail(String email);
}
