package com.baeldung.app.domain.adapters;

import java.util.List;

import com.baeldung.app.domain.User;
import com.baeldung.app.domain.ports.UserRepository;
import com.baeldung.app.domain.ports.UserService;

public class UserServiceAdapter implements UserService {

    private UserRepository userRepository;

    public UserServiceAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

}
