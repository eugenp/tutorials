package com.baeldung.repository;

import com.baeldung.domain.User;

import java.util.List;

public interface IUserRepository {

    User createUserRepo(User user);
    List<User> getUserRepo ();
}
