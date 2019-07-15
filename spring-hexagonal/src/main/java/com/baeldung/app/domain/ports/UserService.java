package com.baeldung.app.domain.ports;

import java.util.List;

import com.baeldung.app.domain.User;

public interface UserService {
    List<User> getUsers();
}
