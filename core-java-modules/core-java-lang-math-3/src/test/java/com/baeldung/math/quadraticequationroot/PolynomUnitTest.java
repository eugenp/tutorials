package com.baeldung.math.quadraticequationroot;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PolynomUnitTest {

    @Test
    void givenaEqualTo0_WhenNewPolynom_ThenThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Polynom(0, 1, 1));
    }

}
