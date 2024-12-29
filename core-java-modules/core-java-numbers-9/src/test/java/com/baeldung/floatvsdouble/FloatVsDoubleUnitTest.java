package com.baeldung.floatvsdouble;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FloatVsDoubleUnitTest {

    // 2.1. Memory Size - Testing memory size impact
    @Test
    public void givenMemorySize_whenComparingFloatAndDouble_thenDoubleRequiresMoreMemory() {
        // Assert that float occupies 4 bytes and double occupies 8 bytes of memory
        assertEquals(4, Float.BYTES, "Float should occupy 4 bytes of memory");
        assertEquals(8, Double.BYTES, "Double should occupy 8 bytes of memory");
    }


    // 2.2. Precision - Testing precision behavior of float and double
    @Test
    public void givenPrecisionLimits_whenExceedingPrecision_thenFloatTruncatesAndDoubleMaintains() {
        float floatValue = 1.123456789f; // Exceeds 7 digits of precision
        assertEquals(1.1234568f, floatValue, "Float precision should truncate beyond 7 digits");

        double doubleValue = 1.123456789123456; // Exceeds 15 digits of precision
        assertEquals(1.123456789123456, doubleValue, "Double precision should maintain up to 15 digits");
    }

    // 2.3. Range - Testing range behavior of float and double
    @Test
    public void givenRangeLimits_whenExceedingBounds_thenFloatUnderflowsAndDoubleHandles() {
        float largeFloat = 3.4e38f; // Near upper bound of float
        float smallFloat = 1.4e-45f; // Near lower bound of float (denormalized value)
        assertTrue(largeFloat > 0, "Float should handle large positive values");
        assertTrue(smallFloat > 0, "Float should handle very small positive values");

        double largeDouble = 1.7e308; // Near upper bound of double
        double smallDouble = 4.9e-324; // Smallest positive non-zero value for double
        assertTrue(largeDouble > 0, "Double should handle extremely large values");
        assertTrue(smallDouble > 0, "Double should handle extremely small positive values");
    }

    // 3. Common Pitfalls - Testing underflow for very small float values
    @Test
    public void givenUnderflowScenario_whenExceedingFloatRange_thenFloatUnderflowsToZero() {
        float underflowValue = 1.4e-45f / 2; // Smaller than the smallest normalized float value
        assertEquals(0.0f, underflowValue, "Float should underflow to zero for values smaller than the smallest representable number");
    }
}
