package org.baeldung.persistence.dao;

import java.util.List;

import org.baeldung.persistence.model.User;

public interface IUserDAO {
    List<User> searchUser(String first, String last, int minAge);

    void save(User entity);
}
