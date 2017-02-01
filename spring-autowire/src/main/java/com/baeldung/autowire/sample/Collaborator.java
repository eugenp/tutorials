package com.baeldung.autowire.sample;

import org.springframework.stereotype.Component;

@Component
public class Collaborator {

    public String doSomething() {
        return "collaborator used";
    }
}
