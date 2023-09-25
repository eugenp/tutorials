package com.baeldung.booleans;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BooleanTrueVsTrueUnitTest {


    @Test
    public void given_BooleanValues_whenUsingBooleanTrue_thenTestBooleanEquality() {
        assertEquals(Boolean.TRUE, Boolean.valueOf(true));
    }

    @Test
    public void given_BooleanValues_whenUsingBooleanTrue_thenTestBooleanIdentity() {
        assertTrue(Boolean.TRUE == Boolean.valueOf(true));
    }

    @Test
    public void given_TrueValue_whenUsingTrue_thenTestPrimitiveEquality() {
        assertTrue(true == true);
    }

    @Test
    public void given_TrueStringValue_whenUsingBooleanTrue_thenTestBooleanToString() {
        assertEquals("true", Boolean.TRUE.toString());
    }

    @Test
    public void given_PrimitiveValue_whenUsingStringValueOf_thenTestPrimitiveToString() {
        assertEquals("true", String.valueOf(true));
    }

}
