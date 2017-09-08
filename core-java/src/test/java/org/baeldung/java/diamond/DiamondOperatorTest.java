package org.baeldung.java.diamond;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class DiamondOperatorTest {
    @Test
    public void shouldCreateCarUsingDiamondWithoutWarnings() {
        Car<Diesel> myCar = new Car<>();
        assertNotNull(myCar);
    }
}
