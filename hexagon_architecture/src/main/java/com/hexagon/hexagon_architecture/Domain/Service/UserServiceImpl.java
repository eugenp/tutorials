package com.hexagon.hexagon_architecture.Domain.Service;

import java.util.List;
import java.util.Optional;

import com.hexagon.hexagon_architecture.Domain.Model.User;
import com.hexagon.hexagon_architecture.Domain.Ports.UserRepository;
import com.hexagon.hexagon_architecture.Domain.Ports.UserService;

public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.getUser();
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepository.getUserById(id);
    }

    @Override
    public void addUser(User user) {
        userRepository.addUser(user);

    }

}
