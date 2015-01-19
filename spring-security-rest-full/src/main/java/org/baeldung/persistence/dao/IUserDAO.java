package org.baeldung.persistence.dao;

import java.util.List;
import java.util.Map;

import org.baeldung.persistence.model.User;

public interface IUserDAO {
    List<User> searchUser(Map<String, Object> params);

    void save(User entity);
}
