package com.baeldung.booleans;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BooleanTruevsTrueUnitTest {

    @Test
    public void testBooleanEquality() {
        assertEquals(Boolean.TRUE, Boolean.valueOf(true));
    }

    @Test
    public void testBooleanIdentity() {
        assertTrue(Boolean.TRUE == Boolean.valueOf(true));
    }

    @Test
    public void testPrimitiveEquality() {
        assertTrue(true == true);
    }

    @Test
    public void testBooleanToString() {
        assertEquals("true", Boolean.TRUE.toString());
    }

    @Test
    public void testPrimitiveToString() {
        assertEquals("true", String.valueOf(true));
    }

}
