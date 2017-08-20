package com.baeldung.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MegaTestService {
    private TestService testService;

    @Autowired
    public void setTestService(TestService testService) {
        this.testService = testService;
    }

    public String getMegaTestOne() {
        return "Mega" + testService.getTestOne();
    }

    public String getMegaTestTwo() {
        return "Mega" + testService.getTestTwo();
    }
}
