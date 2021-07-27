package com.baeldung.hexagonal.inside.port.outbound;

import com.baeldung.hexagonal.inside.domain.User;

import java.util.List;

public interface UserDaoPort {
    void addUser(User user);

    List<User> getList();
}