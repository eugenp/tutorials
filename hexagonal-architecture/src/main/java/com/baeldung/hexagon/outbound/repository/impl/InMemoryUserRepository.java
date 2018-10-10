package com.baeldung.hexagon.outbound.repository.impl;

import com.baeldung.hexagon.core.User;
import com.baeldung.hexagon.outbound.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ali Dehghani
 */
@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Set<User> users = new HashSet<>();

    @Override
    public User create(User user) {
        users.add(user);
        return user;
    }

    @Override
    public boolean delete(User user) {
        return users.remove(user);
    }

    @Override
    public Collection<User> list() {
        return Collections.unmodifiableSet(users);
    }
}
