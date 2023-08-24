package com.baeldung.javadoublevsbigdecimal;

import com.baeldung.javadoublevsbigdecimal.DoubleExample
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DoubleExampleUnitTest {

    @Test
    void whenComparingDoubleValues_thenValuesAreApproximatelyEqual() {
        double d = 0.1;

        double result = DoubleExample.performDoubleComparison(d);

        assertEquals(d, result, 0.0000000000000001);
    }
}
