package org.baeldung.hexagonal.domain.repository;

import org.baeldung.hexagonal.domain.core.User;

public interface UserRepository {

    void storeUser(User user);

}
