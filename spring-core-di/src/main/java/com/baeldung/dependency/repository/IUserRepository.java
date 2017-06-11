package com.baeldung.dependency.repository;

import com.baeldung.dependency.domain.User;


public interface IUserRepository {

    void save(User user);

    User findById(Integer id);

    void deleteById(Integer id);

}
