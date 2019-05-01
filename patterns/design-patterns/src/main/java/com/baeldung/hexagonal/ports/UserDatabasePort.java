package com.baeldung.hexagonal.ports;

import com.baeldung.hexagonal.domain.User;

public interface UserDatabasePort {
    void save(User user);
    // other operations
}
