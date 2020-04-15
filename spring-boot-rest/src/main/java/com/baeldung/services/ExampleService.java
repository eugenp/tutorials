package com.baeldung.services;

import org.springframework.stereotype.Service;

import com.baeldung.requestresponsebody.LoginForm;

@Service
public class ExampleService {

    public boolean fakeAuthenticate(LoginForm lf) {
        return true;
    }
}