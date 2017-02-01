package com.baeldung.di.constructor;

import com.baeldung.di.model.User;
import com.baeldung.di.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by DevTiwari on 23-01-17.
 */
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User getUser(Long userId){
        User user = userRepository.getUserById(userId);
        return user;
    }
}
