package com.baeldung.micronaut.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;

@MicronautTest(startApplication = false)
class AdditionServiceUnitTest {

    @Inject
    AdditionService additionService;

    @Test
    void givenAdditionService_whenAddingTwoIntegers_thenReturnSum() {
        assertEquals(4, additionService.sum(2, 2));
    }
}

