package com.baeldung.dddmodules.hexagonalarchitecture.adapters;

import com.baeldung.dddmodules.hexagonalarchitecture.ports.UserPersistence;
import com.baeldung.dddmodules.hexagonalarchitecture.core.User;

import java.util.UUID;

class InMemoryUserPersistenceImpl implements UserPersistence {
    @Override
    public User createUser(User user) {
        user.setId(UUID.randomUUID());
        return user;
    }
}
