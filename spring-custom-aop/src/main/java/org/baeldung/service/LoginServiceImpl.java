package org.baeldung.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private CounterService counterService;

    @Autowired
    public LoginServiceImpl(CounterService counterService) {
        this.counterService = counterService;
    }

    public boolean login(String userName, char[] password) {
        boolean success;
        if (userName.equals("admin") && "secret".toCharArray().equals(password)) {
            counterService.increment("counter.login.success");
            success = true;
        } else {
            counterService.increment("counter.login.failure");
            success = false;
        }
        return success;
    }

}
