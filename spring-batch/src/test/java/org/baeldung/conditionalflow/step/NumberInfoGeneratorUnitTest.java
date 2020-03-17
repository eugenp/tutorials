package org.baeldung.conditionalflow.step;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.baeldung.conditionalflow.model.NumberInfo;
import org.junit.jupiter.api.Test;

public class NumberInfoGeneratorUnitTest {
    @Test
    public void testGenerateNumbers_correctOrderAndValue() {
        int[] numbers = new int[]{1, -2, 4, -10};
        NumberInfoGenerator numberGenerator = new NumberInfoGenerator(numbers);
        assertEquals(new NumberInfo(numbers[0]), numberGenerator.read());
        assertEquals(new NumberInfo(numbers[1]), numberGenerator.read());
        assertEquals(new NumberInfo(numbers[2]), numberGenerator.read());
        assertEquals(new NumberInfo(numbers[3]), numberGenerator.read());
        assertNull(numberGenerator.read());
    }
}