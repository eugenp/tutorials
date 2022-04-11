package com.baeldung.openid.oidc.jwtauthorities.service;

import org.springframework.stereotype.Service;

import com.baeldung.openid.oidc.jwtauthorities.domain.Account;

@Service
public class AccountService {
    
    public Account findAccountByPrincipal(String principal) {
        // NOTE: real-world code would typically perform some sort of DB lookup
        return new Account(1l, "0001", "101888444-0");
    }
}
