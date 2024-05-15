package com.baeldung.enablemethodsecurity.services;

import org.springframework.stereotype.Service;

@Service
public class PolicyService {
    @Policy(PolicyEnum.OPEN)
    public String openPolicy() {
        return "Open Policy Service";
    }

    @Policy(PolicyEnum.RESTRICTED)
    public String restrictedPolicy() {
        return "Restricted Policy Service";
    }
}