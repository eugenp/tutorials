package com.baeldung.dependency.service;

import com.baeldung.dependency.domain.User;

public interface IUserService {

    void createUser(User user);

    void deleteUser(User user);

    User findById(Integer id);

}
