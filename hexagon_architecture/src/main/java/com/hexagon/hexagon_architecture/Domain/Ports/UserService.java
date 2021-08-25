package com.hexagon.hexagon_architecture.Domain.Ports;

import java.util.List;
import java.util.Optional;

import com.hexagon.hexagon_architecture.Domain.Model.User;

public interface UserService {
    
    List<User> getUsers();
    Optional<User> getUserById(String id);
    void addUser(User user);
}
