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
    public void givenFloatValueWithinRange_whenClamp_thenReturnValue() {
        assertEquals(16.2f, clampValue.clamp(16.2f, 15f, 35.3f));
    }

    /*
     * This method uses clamp method introduce in Java 21 which is still hypothetical 
    @Test
    public void givenValueWithinRange_whenClamp_thenReturnValue() {
        assertEquals(20, Math.clamp(20, 67,98));
    }
    */

}
