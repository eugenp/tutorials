package com.baeldung.hexagon.core;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Ali Dehghani
 */
public interface UserPort {

    void addUser(User user);

    void editUser(User user, UserEdit edit);

    Collection<User> all();

    Optional<User> find(Long id);
}
