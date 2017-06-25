package org.baeldung.persistence.dao;

import java.util.List;

import org.baeldung.persistence.model.User;
import org.baeldung.web.util.SearchCriteria;

public interface IUserDAO {
    List<User> searchUser(List<SearchCriteria> params);

    void save(User entity);
}
