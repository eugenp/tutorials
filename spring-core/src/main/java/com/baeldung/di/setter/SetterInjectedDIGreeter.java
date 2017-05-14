package com.baeldung.di.setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.di.GreetingService;

@Component
public class SetterInjectedDIGreeter {
    private GreetingService greetingService;
    
    public GreetingService getGreetingService() {
        return greetingService;
    }
    
    @Autowired
    public void setGreetingService(GreetingService greetingService) {
        this.greetingService = greetingService;
    }
    
    public String greet(String name) {
        return getGreetingService().greet(name);
    }
}
