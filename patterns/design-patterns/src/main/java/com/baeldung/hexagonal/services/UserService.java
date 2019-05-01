package com.baeldung.hexagonal.services;

import com.baeldung.hexagonal.adapters.InMemoryUserAdapter;
import com.baeldung.hexagonal.domain.User;
import com.baeldung.hexagonal.ports.UserDatabasePort;

public class UserService {
    
    private UserDatabasePort port = new InMemoryUserAdapter();

    public void create(User user) {
        port.save(user);
    }

    // other operations

}
