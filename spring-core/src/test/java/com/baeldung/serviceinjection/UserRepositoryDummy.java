package com.baeldung.serviceinjection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserRepositoryDummy implements Repository<User> {

    private final List<User> userList;

    public UserRepositoryDummy() {
        this.userList = Collections.emptyList();
    }

    public UserRepositoryDummy(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public List<User> findAll() {
        return userList;
    }

}
