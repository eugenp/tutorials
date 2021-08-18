package com.hexagon.hexagon_architecture.Domain.Ports;

import java.util.List;
import java.util.Optional;

import com.hexagon.hexagon_architecture.Domain.Model.User;

public interface UserRepository {
    List<User> getUser();
    
    Optional<User> getUserById(String id);
    
    void addUser(User user);
}
