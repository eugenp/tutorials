package com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.port.repository;

import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.User;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.port.repository.exceptions.UserRepositoryException;

public interface IUserRepositoryAdapter {
    User get(long userId);

    User save(User user);

    void remove(User user) throws UserRepositoryException;
}
