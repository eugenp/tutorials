package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.model.User;
import com.baeldung.hexagonal.port.UserDatabasePort;

public class PostgresDatabaseAdapter implements UserDatabasePort {

    @Override
    public User getUser(String username) {
        User postgresUser = new User();
        postgresUser.setToken(username + " Token fetched from Postgres Db");
        return postgresUser;
    }
}