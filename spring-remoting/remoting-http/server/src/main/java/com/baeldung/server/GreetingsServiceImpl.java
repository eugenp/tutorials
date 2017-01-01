package com.baeldung.server;

import com.baeldung.api.GreetingsService;

public class GreetingsServiceImpl implements GreetingsService {
    @Override public String sayHello() {
        return "Hello " + Math.random();
    }
}
