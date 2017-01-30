package com.baeldung.service;

import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service("UserService")
public class UserService implements AccountService {

    @Override
    public String getRole() {
        return "ROLE_USER";
    }

}
