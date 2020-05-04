package com.baeldung.architecture.hexagonal.core.services.ports;

import com.baeldung.architecture.hexagonal.core.domain.User;

public interface UserServicePort {
    void save(User user);
    User get(Long id);
}
