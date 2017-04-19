package org.baeldung.repository;

import org.baeldung.model.User;

public interface UserRepositoryCustom {
    public User findUserCustom(String id);
}
