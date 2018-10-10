package com.baeldung.hexagon.outbound.repository;

import com.baeldung.hexagon.core.User;

import java.util.Collection;

/**
 * @author Ali Dehghani
 */
public interface UserRepository {

    User create(User user);

    boolean delete(User user);

    Collection<User> list();
}
