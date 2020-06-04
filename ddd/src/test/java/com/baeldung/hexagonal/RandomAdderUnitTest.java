package com.baeldung.hexagonal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RandomAdderUnitTest {

    @Test
    void whenAddRandom16dot5_then17() {
        RandomAdder randomAdder = new AdderConfig().test();
        double result = randomAdder.addRandom(16.26912180929671);
        assertEquals(17, result);
    }
}