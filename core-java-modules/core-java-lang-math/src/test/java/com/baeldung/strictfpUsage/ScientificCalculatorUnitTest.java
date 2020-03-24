package com.baeldung.strictfpUsage;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ScientificCalculatorUnitTest {

    @Test
    public void whenMethodOfstrictfpClassInvoked_thenIdenticalResultOnAllPlatforms() {
        ScientificCalculator calculator = new ScientificCalculator();
        double result = calculator.sum(23e10, 98e17);
        assertThat(result, is(9.800000230000001E18));
        result = calculator.diff(Double.MAX_VALUE, 1.56);
        assertThat(result, is(1.7976931348623157E308));
    }
}
