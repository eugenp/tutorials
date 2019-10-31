package com.baeldung.diamondoperator;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class DiamondOperatorUnitTest {
    @Test
    public void whenCreateCarUsingDiamondOperator_thenSuccess() {
        Car<Diesel> myCar = new Car<>();
        assertNotNull(myCar);
    }
}
