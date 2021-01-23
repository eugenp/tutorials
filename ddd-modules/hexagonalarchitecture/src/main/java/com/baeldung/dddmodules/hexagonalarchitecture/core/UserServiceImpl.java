package com.baeldung.dddmodules.hexagonalarchitecture.core;

import com.baeldung.dddmodules.hexagonalarchitecture.ports.UserPersistence;
import com.baeldung.dddmodules.hexagonalarchitecture.ports.UserService;

public class UserServiceImpl implements UserService {
    private UserPersistence userPersistence;

    public UserServiceImpl(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    @Override
    public User createUser(User userToCreate) {
        return userPersistence.createUser(userToCreate);
    }
}
