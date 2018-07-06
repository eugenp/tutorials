package com.baeldung.micronaut.server.service;

import javax.inject.Singleton;

@Singleton
public class SpanishGreetingService implements GreetingService {
    @Override
    public String getGreeting() {
        return "Hola ";
    }
}
