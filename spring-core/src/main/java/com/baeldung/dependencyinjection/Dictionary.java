package com.baeldung.dependencyinjection;

import org.springframework.stereotype.Component;

@Component
public class Dictionary {

    public String hello() {
        return "Hello from dictionary";
    }
}
