package com.baeldung.hexagonal.outside.driven.mockdb;

import com.baeldung.hexagonal.inside.domain.User;
import com.baeldung.hexagonal.inside.port.outbound.UserDaoPort;

import java.util.List;

public class UserDaoAdapter implements UserDaoPort {
    @Override
    public void addUser(User user) {
        MockRepository.saveUser(user);
    }

    @Override
    public List<User> getList() {
        return MockRepository.getUserList();
    }
}