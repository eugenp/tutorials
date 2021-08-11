package com.baeldung.hexagonal.repository.impl;

import com.baeldung.hexagonal.domain.User;
import com.baeldung.hexagonal.repository.UserDaoInterface;

public class UserDaoMock implements UserDaoInterface{
    @Override
    public User getUserById(int id) {
        return new User(11,"Yana","Samson");
    }
}
