package com.baeldung.mockito.junit5.repository;

import com.baeldung.mockito.junit5.User;

public interface UserRepository {

    User insert(User user);

    boolean isUsernameAlreadyExists(String userName);

}
