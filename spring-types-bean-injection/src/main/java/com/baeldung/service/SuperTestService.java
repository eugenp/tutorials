package com.baeldung.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SuperTestService {
    private TestService testService;

    @Autowired
    public SuperTestService(TestService testService) {
        this.testService = testService;
    }

    public String getSuperTestOne() {
        return "Super" + testService.getTestOne();
    }

    public String getSuperTestTwo() {
        return "Super" + testService.getTestTwo();
    }
}
