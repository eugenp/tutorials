package com.baeldung.micronaut.micronautjunit;

import jakarta.inject.Singleton;

@Singleton
public class AdditionService {

    public Integer sum(int a, int b) {
        return a + b;
    }

}
