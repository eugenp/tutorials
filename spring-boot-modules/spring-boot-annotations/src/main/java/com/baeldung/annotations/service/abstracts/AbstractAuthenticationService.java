package com.baeldung.annotations.service.abstracts;

import org.springframework.stereotype.Service;

@Service
public abstract class AbstractAuthenticationService {

    public boolean authenticate(String username, String password) {
        return false;
    }

}