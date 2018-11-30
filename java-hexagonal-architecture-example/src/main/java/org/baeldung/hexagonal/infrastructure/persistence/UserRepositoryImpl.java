package org.baeldung.hexagonal.infrastructure.persistence;

import org.baeldung.hexagonal.domain.core.User;
import org.baeldung.hexagonal.domain.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public void storeUser(User user) {
        System.out.println(user.getUsername() + " has been stored in the repo.");
    }
}
