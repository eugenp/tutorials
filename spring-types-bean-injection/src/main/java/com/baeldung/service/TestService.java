package com.baeldung.service;

import org.springframework.stereotype.Service;

@Service
public class TestService {
    public String getTestOne() {
        return "TestOne";
    }

    public String getTestTwo() {
        return "TestTwo";
    }
}
