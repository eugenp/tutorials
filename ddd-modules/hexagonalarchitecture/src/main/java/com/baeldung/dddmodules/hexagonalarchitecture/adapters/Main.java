package com.baeldung.dddmodules.hexagonalarchitecture.adapters;

import com.baeldung.dddmodules.hexagonalarchitecture.core.User;
import com.baeldung.dddmodules.hexagonalarchitecture.core.UserServiceImpl;
import com.baeldung.dddmodules.hexagonalarchitecture.ports.UserService;

public class Main {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl(new InMemoryUserPersistenceImpl());
        User user = userService.createUser(new User("John", "Doe"));
    }
}
