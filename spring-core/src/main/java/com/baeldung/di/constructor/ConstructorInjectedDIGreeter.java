package com.baeldung.di.constructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.di.GreetingService;

@Component
public class ConstructorInjectedDIGreeter {
    private final GreetingService greetingService;
    
    @Autowired
    public ConstructorInjectedDIGreeter(GreetingService greetingService) {
        this.greetingService = greetingService;
    }
    
    public String greet(String name) {
        return greetingService.greet(name);
    }
}
