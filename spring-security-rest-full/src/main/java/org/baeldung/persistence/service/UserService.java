package org.baeldung.persistence.service;

import org.baeldung.persistence.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User save(User user);
}
