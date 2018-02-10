package com.baeldung.junit5.mockito.repository;

import com.baeldung.junit5.mockito.User;

public interface UserRepository {

    User insert(User user);
    boolean isUsernameAlreadyExists(String userName);
    
}
