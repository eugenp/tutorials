package com.baeldung.persistence.dao;

import java.util.List;

import com.baeldung.persistence.model.User;
import com.baeldung.web.util.SearchCriteria;

public interface IUserDAO {
    List<User> searchUser(List<SearchCriteria> params);

    void save(User entity);
}
