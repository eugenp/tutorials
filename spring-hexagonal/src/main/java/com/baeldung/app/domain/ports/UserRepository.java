package com.baeldung.app.domain.ports;

import java.util.List;

import com.baeldung.app.domain.User;

public interface UserRepository {
    List<User> getUsers();
}
