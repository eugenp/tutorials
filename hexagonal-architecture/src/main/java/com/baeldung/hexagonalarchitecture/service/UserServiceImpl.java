package com.baeldung.hexagonalarchitecture.service;

import com.baeldung.hexagonalarchitecture.dao.UserRepository;
import com.baeldung.hexagonalarchitecture.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserServiceInterface {

    @Autowired
    UserRepository userRepository;

    @Override
    public User addUser(User user) { return userRepository.save(user); }

    @Override
    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }
}
