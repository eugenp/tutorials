package com.baeldung.cucumber.tags.service;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomNumberGeneratorServiceUnitTest {

    private final RandomNumberGeneratorService tested = new RandomNumberGeneratorService();

    @Test
    public void generateRandomNumberReturnsOK() {

        int actual = tested.generateRandomNumber(1,5);
        assertTrue(actual>=1 && actual<=5);
    }
}