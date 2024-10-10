package com.baeldung.floattoint;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConvertFloatToIntUnitTest {

    // Static float values for reuse
    private static final float VALUE_1 = 7.9f;
    private static final float VALUE_2 = 5.4f;
    private static final float VALUE_3 = -5.1f;

    // Test for explicit type casting
    @Test
    public void givenFloatValues_whenExplicitCasting_thenValuesAreTruncated() {
        // When
        int intValue1 = (int) VALUE_1;
        int intValue2 = (int) VALUE_2;
        int intValue3 = (int) VALUE_3;

        // Then
        assertEquals(7, intValue1);
        assertEquals(5, intValue2);
        assertEquals(-5, intValue3);
    }

    // Test for rounding using Math.round()
    @Test
    public void givenFloatValues_whenRounding_thenValuesAreRoundedToNearestInteger() {
        // When
        int roundedValue1 = Math.round(VALUE_1);
        int roundedValue2 = Math.round(VALUE_2);
        int roundedValue3 = Math.round(VALUE_3);

        assertEquals(8, roundedValue1);
        assertEquals(5, roundedValue2);
        assertEquals(-5, roundedValue3);
    }

    // Test for flooring using Math.floor()
    @Test
    public void givenFloatValues_whenFlooring_thenValuesAreRoundedDownToNearestWholeNumber() {
        // When
        int flooredValue1 = (int) Math.floor(VALUE_1);
        int flooredValue2 = (int) Math.floor(VALUE_2);
        int flooredValue3 = (int) Math.floor(VALUE_3);

        assertEquals(7, flooredValue1);
        assertEquals(5, flooredValue2);
        assertEquals(-6, flooredValue3);
    }

    // Test for ceiling using Math.ceil()
    @Test
    public void givenFloatValues_whenCeiling_thenValuesAreRoundedUpToNearestWholeNumber() {
        // When
        int ceiledValue1 = (int) Math.ceil(VALUE_1);
        int ceiledValue2 = (int) Math.ceil(VALUE_2);
        int ceiledValue3 = (int) Math.ceil(VALUE_3);

        assertEquals(8, ceiledValue1);
        assertEquals(6, ceiledValue2);
        assertEquals(-5, ceiledValue3);
    }
}
