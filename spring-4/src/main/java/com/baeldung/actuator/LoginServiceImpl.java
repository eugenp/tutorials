package com.baeldung.actuator;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class LoginServiceImpl {

    private final CounterService counterService;

    public LoginServiceImpl(CounterService counterService) {
        this.counterService = counterService;
    }

    public boolean login(String userName, char[] password) {
        boolean success;
        if (userName.equals("admin") && Arrays.equals("secret".toCharArray(), password)) {
            counterService.increment("counter.login.success");
            success = true;
        } else {
            counterService.increment("counter.login.failure");
            success = false;
        }
        return success;
    }
}