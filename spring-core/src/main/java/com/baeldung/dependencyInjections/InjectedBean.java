package com.baeldung.dependencyInjections;

import org.springframework.stereotype.Component;

@Component
public class InjectedBean {

    public InjectedBean() {
        System.out.println("InjectedBean: Constructor called.");
    }
    
    @Override
    public String toString() {
        return "InjectedBean dependency injected.";
    }

}
