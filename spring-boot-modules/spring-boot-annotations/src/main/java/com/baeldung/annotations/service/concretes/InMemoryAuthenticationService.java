package com.baeldung.annotations.service.concretes;

import com.baeldung.annotations.service.interfaces.AuthenticationService;
import org.springframework.stereotype.Service;

@Service
public class InMemoryAuthenticationService implements AuthenticationService {

    @Override
    public boolean authenticate(String username, String password) {
        return false;
    }

}