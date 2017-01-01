package com.baeldung;

import com.baeldung.api.GreetingsService;

public class GreetingsServiceImpl implements GreetingsService {
    @Override public String sayHello() {
        return "Hello " + Math.random();
    }
}
