package com.baeldung.quarkus;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HelloService {

    @ConfigProperty(name = "greeting")
    String greeting;

    public String politeHello(String name){
        return greeting + " " + name;
    }
}
