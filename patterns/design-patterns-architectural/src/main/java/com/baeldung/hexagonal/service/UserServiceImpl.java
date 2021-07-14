package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.domain.MemeberStatus;
import com.baeldung.hexagonal.domain.User;
import com.baeldung.hexagonal.exception.UserNotFoundException;
import com.baeldung.hexagonal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    public User registerUser(User user) {

        return repo.CreateUser(user);

    }

    public User upgradeUser(User user) throws UserNotFoundException {

        if (user.getStatus() == MemeberStatus.BRONZE) {
            user.setStatus(MemeberStatus.SILVER);
        } else if (user.getStatus() == MemeberStatus.SILVER) {
            user.setStatus(MemeberStatus.GOLD);
        } else if (user.getStatus() == MemeberStatus.GOLD) {
            user.setStatus(MemeberStatus.GOLD);
        }

        return repo.UpdateUser(user);
    }

    public List<User> getAllUsers() {
        return repo.findAll();
    }

    public Optional<User> findUserById(int userId) {
        return repo.findCustomerById(userId);
    }

}
