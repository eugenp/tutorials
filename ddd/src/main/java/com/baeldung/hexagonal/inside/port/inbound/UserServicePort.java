package com.baeldung.hexagonal.inside.port.inbound;

import com.baeldung.hexagonal.inside.domain.User;

import java.util.List;

public interface UserServicePort {
    boolean addUser(String name);

    List<User> getUsers();
}
