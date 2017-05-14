package com.baeldung.di;

public class WelcomeGreetingService implements GreetingService {

    @Override
    public String greet(String name) {
        return "Hello ".concat(name);
    }

}
