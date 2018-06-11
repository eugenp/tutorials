package com.baeldung.web;

import org.springframework.stereotype.Service;

import com.baeldung.services.IHomeService;

@Service
public class GreetingService implements IHomeService {

    public String getGreeting() {
        return "Greetings for the day";
    }
}
