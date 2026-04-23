package com.baeldung.floatvsdouble;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
        float floatValue = 1.123456789f; // Exceeds 7 digits of precision for float
        // Expected float value should round/truncate after 7 digits (i.e., 1.1234568)
        assertEquals(1.1234568f, floatValue, "Float should truncate beyond 7 digits");

        double doubleValue = 1.1234567891234566d; // Exceeds 15 digits of precision for double
        // Double rounds after the 15th digit, so the expected value is 1.123456789123457
        assertEquals(1.123456789123457, doubleValue, 1e-15, "Double should round beyond 15 digits");
    }

    // 2.3. Range - Testing range behavior of float and double
    @Test
    public void givenRangeLimits_whenExceedingBounds_thenFloatUnderflowsAndDoubleHandles() {
        float largeFloat = 3.4e38f;
        assertTrue(largeFloat > 0, "Float should handle large positive values");
        float smallFloat = 1.4e-45f;
        assertTrue(smallFloat > 0, "Float should handle very small positive values");

        double largeDouble = 1.7e308;
        assertTrue(largeDouble > 0, "Double should handle extremely large values");
        double smallDouble = 4.9e-324;
        assertTrue(smallDouble > 0, "Double should handle extremely small positive values");
    }

    // 3. Common Pitfalls - Testing underflow for very small float values
    @Test
    public void givenUnderflowScenario_whenExceedingFloatRange_thenFloatUnderflowsToZero() {
        float underflowValue = 1.4e-45f / 2; // Smaller than the smallest normalized float value
        assertEquals(0.0f, underflowValue, "Float should underflow to zero for values smaller than the smallest representable number");
    }

    // 4. Equality Pitfalls
    @Test
    public void givenDecimalValues_whenAdding_thenEqualityCheckFails() {
        double value = 0.1d + 0.2d;
        assertNotEquals(0.3d, value, "The binary form introduces a small rounding difference");
        assertEquals(0.3d, value, 1e-15, "A comparison using a tolerance verifies the expected result");
    }

    // 5. Compiler and Literal Rules
    @Test
    public void givenFloatAssignment_whenUsingLiteral_thenSuffixIsRequired() {
        // float f = 1.0; // Compilation error because the literal is a double
        float f = 1.0f;
        assertEquals(1.0f, f, "Float literal using 'f' compiles correctly");
    }
}
