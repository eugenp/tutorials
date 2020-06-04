package com.baeldung.hexagonal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RandomAdderUnitTest {

    @Test
    void addRandomTest() {
        RandomAdder randomAdder = new AdderConfig().test();
        double result;

        // We only test business logic here
        result = randomAdder.addRandom(16.5);
        assertEquals(17, result);

        result = randomAdder.addRandom(0d);
        assertEquals(0.5, result);

        result = randomAdder.addRandom(-0.3);
        assertEquals(0.2, result);
    }
}
