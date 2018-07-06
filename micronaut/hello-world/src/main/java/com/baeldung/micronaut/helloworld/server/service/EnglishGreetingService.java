package com.baeldung.micronaut.helloworld.server.service;

import io.micronaut.context.annotation.Primary;

import javax.inject.Singleton;

@Primary
@Singleton
public class EnglishGreetingService implements GreetingService {
    @Override
    public String getGreeting() {
        return "Hello ";
    }
}
