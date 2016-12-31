package com.baeldung;


public class GreetingsServiceImpl implements GreetingsService {
    @Override public String sayHello() {
        return "Hello " + Math.random();
    }
}
