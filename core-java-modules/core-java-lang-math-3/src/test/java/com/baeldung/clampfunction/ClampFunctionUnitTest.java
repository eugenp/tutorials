package com.baeldung.clampfunction;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ClampFunctionUnitTest {

    Clamp clampValue = new Clamp();

    @Test
    public void givenValueOutsideRange_whenClamp_thenReturnLowerValue() {
        assertEquals(15, clampValue.clamp(10, 15, 35));
    }

    @Test
    public void givenValueWithinRange_whenClamp_thenReturnValue() {
        assertEquals(20, clampValue.clamp(20, 15, 35));
    }

    @Test
    public void givenValueOutsideRange_whenClamp_thenReturnMaximumValue() {
        assertEquals(35, clampValue.clamp(50, 15, 35));
    }

    @Test
    public void givenDoubleValueOutsideRange_whenClamp_thenReturnMaximumValue() {
        assertEquals(60.5, clampValue.clamp(75.6, 25.5, 60.5));
    }

    /*
     * This method uses the clamp() method introduced in Java 21
    @Test
    public void givenValueWithinRange_whenClamp_thenReturnValue() {
        assertEquals(20, Math.clamp(20, 17, 98));
    }
    */

}
