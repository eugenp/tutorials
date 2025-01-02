package com.baeldung.annotations.service.concretes;

import org.springframework.stereotype.Service;

import com.baeldung.annotations.service.interfaces.AuthenticationService;

@Service
public class InMemoryAuthenticationService implements AuthenticationService {

    @Override
    public boolean authenticate(String username, String password) {
        return false;
    }

}