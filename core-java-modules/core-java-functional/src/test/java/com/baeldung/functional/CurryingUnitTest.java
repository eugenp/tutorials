package com.baeldung.functional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class CurryingUnitTest {

    @Test
    public void testWeightOnEarth() {

        assertEquals(588.6, Currying.weightOnEarth(60.0), 0.1);

    }

    @Test
    public void testWeightOnMars() {

        assertEquals(225.0, Currying.weightOnMars(60.0), 0.1);

    }
}
