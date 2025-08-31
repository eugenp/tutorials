package com.baeldung.junit5.repository;

import com.baeldung.junit5.User;

public interface UserRepository {

    User insert(User user);

    boolean isUsernameAlreadyExists(String userName);

}
