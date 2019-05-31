package com.baeldung.hexagonalarch.service;

import com.baeldung.hexagonalarch.model.User;
import com.baeldung.hexagonalarch.port.UserRepoPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepoPort userRepo;

    public void create(User user) {
        userRepo.create(user);
    }

    public User getUser(Integer userid) {
        return userRepo.getUser(userid);
    }
}
