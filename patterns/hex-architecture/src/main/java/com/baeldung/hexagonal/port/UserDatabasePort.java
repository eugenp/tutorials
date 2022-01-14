package com.baeldung.hexagonal.port;

import com.baeldung.hexagonal.model.User;

public interface UserDatabasePort {

    User getUser(String username);

}