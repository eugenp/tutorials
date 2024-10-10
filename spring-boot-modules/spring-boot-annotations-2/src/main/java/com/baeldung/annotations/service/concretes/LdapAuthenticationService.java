package com.baeldung.annotations.service.concretes;

import org.springframework.stereotype.Service;

import com.baeldung.annotations.service.abstracts.AbstractAuthenticationService;

@Service
public class LdapAuthenticationService extends AbstractAuthenticationService {

    @Override
    public boolean authenticate(String username, String password) {
        return true;
    }

}