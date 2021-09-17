package com.baeldung.architecture.clean.hexagonal.entitybased.user.application.port.dao;

import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model.User;

public interface IUserDAO {
    User select(long userId);

    User upsert(User user);

    void delete(User user);
}
