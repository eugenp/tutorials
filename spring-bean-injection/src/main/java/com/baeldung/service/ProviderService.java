package com.baeldung.service;

import org.springframework.stereotype.Service;

@Service("ProviderService")
public class ProviderService implements AccountService{

    @Override
    public String getRole() {
        return "ROLE_PROVIDER";
    }
}
