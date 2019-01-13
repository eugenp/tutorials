package com.baeldung.service;

import com.baeldung.domain.User;
import com.baeldung.hexagonal.port.UserDBPort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDBPort userDBrepo;

    public void register(String name){
        userDBrepo.register(name);
    }

    public User view(Integer userId){
        return userDBrepo.getUser(userId);
    }
    
}
