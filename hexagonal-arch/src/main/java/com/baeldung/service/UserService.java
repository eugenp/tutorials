package com.baeldung.service;


import com.baeldung.domain.User;
import com.baeldung.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Override
    public User createUser(User user) {
       return userRepository.createUserRepo(user);
    }

    @Override
    public List<User> getUsers() {
        return  userRepository.getUserRepo();
    }
}
