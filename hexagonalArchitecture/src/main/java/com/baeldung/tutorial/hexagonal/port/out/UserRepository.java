package com.baeldung.tutorial.hexagonal.port.out;

import com.baeldung.tutorial.hexagonal.core.User;

/**
 * User Repository to save the user details.
 * This repository can be mapped to any type of underlying Repository as defined in adapter package.
 * This makes core maintainability easy as its not dependent on how the data is persisted/retrieved into/from repositories
 */
public interface UserRepository {
    void saveUser(User user);
    User getUser(String userEmail);
    boolean deleteUser(String userEmail);
}
