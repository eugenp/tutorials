package com.baeldung.service;

import com.baeldung.domain.User;

import java.util.List;

public interface IUserService {

    User createUser(User user);
    List<User> getUsers();
}
