package com.baeldung.javahexagonal.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.javahexagonal.adapter.repo.UserRepository;
import com.baeldung.javahexagonal.core.domain.User;
import com.baeldung.javahexagonal.core.port.UserRepoOutputPort;

@Component
public class UserRepoDrivenAdapter implements UserRepoOutputPort {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        return user;
    }

    @Override
    public User updateUser(User user) {
        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

}
