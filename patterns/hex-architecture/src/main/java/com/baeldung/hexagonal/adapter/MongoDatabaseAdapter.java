package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.model.User;
import com.baeldung.hexagonal.port.UserDatabasePort;

public class MongoDatabaseAdapter implements UserDatabasePort {

    @Override
    public User getUser(String username) {
        User mongoUser = new User();
        mongoUser.setToken(username + " Token fetched from Mongo Db");
        return mongoUser;
    }
}