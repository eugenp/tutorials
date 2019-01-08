package com.baeldung.hexagonal.services;

import org.springframework.stereotype.Component;

@Component
public class GreetingService {

    public String createHelloGreeting(String nameFirst, String nameLast) {
        return String.format("Hello, %s %s!  How are you today?", nameFirst, nameLast) ;
    }
}
