package com.baeldung.math.bmicalculator;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class BMICalculatorUnitTest {

    @Test
    public void whenBMIIsGreaterThanThirty_thenObese() {

        double weight = 100;
        double height = 1.524;
        String actual = BMICalculator.calculateBMI(weight, height);
        String expected = "Obese";

        assertThat(actual).isEqualTo(expected);

    }
}
