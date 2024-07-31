package com.baeldung.micronaut.helloworld.server.service;

import jakarta.inject.Singleton;

@Singleton
public class SpanishGreetingService implements GreetingService {
    @Override
    public String getGreeting() {
        return "Hola ";
    }
}
