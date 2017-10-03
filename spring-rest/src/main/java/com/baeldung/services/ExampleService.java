package com.baeldung.services;

import com.baeldung.transfer.LoginForm;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    public boolean fakeAuthenticate(LoginForm lf) {
        return true;
    }
}