package com.example.hexagonalarch.bo;

import com.example.hexagonalarch.port.driven.UserRepositoryPort;
import com.example.hexagonalarch.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserBo {
    @Autowired
    private UserRepositoryPort userRepositoryPort;

    public boolean createUser(User user) {
        userRepositoryPort.save(user);
        return true;
    }
}
