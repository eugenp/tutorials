package com.hexagon.hexagon_architecture.Application.Adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.hexagon.hexagon_architecture.Domain.Model.User;
import com.hexagon.hexagon_architecture.Domain.Ports.UserRepository;
import com.hexagon.hexagon_architecture.Framework.Repository.UserMongoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class UserRepositoryImpl implements UserRepository {

    private UserMongoRepository userMongoRepository;

    @Autowired
    public UserRepositoryImpl(UserMongoRepository userMongoRepository) {
        this.userMongoRepository = userMongoRepository;
    }

    @Override
    public List<User> getUser() {
        List<User> users = new ArrayList<>();
        users = userMongoRepository.findAll();
        return users;
    }

    @Override
    public Optional<User> getUserById(String id) {
        Optional<User> user = userMongoRepository.findByUserId(id);
        return user;
    }

    @Override
    public void addUser(User user) {
        userMongoRepository.save(user);

    }

}
