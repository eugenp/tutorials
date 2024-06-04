package com.baeldung.exponentialtonumber;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.junit.jupiter.api.Test;

public class ExponentialToNumberUnitTest {

    double scientificValue = 1.23456789E9;
    String expectedValue = "1234567890";

    @Test
    public void givenScientificValue_whenUtilizingDecimalFormat_thenCorrectNumberFormat() {
        DecimalFormat decimalFormat;

        if (scientificValue >= 1 || scientificValue <= -1) {
            decimalFormat = new DecimalFormat("#");
        } else {
            decimalFormat = new DecimalFormat("0.#################");
        }

        String result = decimalFormat.format(scientificValue);

        assertEquals(expectedValue, result);
    }

    @Test
    public void givenScientificValue_whenUtilizingBigDecimal_thenCorrectNumberFormat() {
        BigDecimal bigDecimalPositive = new BigDecimal(Double.toString(scientificValue));
        String result = bigDecimalPositive.toPlainString();

        assertEquals(expectedValue, result);
    }

    @Test
    public void givenScientificValue_whenUtilizingStringFormat_thenCorrectNumberFormat() {
        String formatResult;
        if (scientificValue >= 1 || scientificValue <= -1) {
            formatResult = String.format("%.0f", scientificValue);
        } else {
            formatResult = String.format("%.17f", scientificValue);
        }

        assertEquals(expectedValue, formatResult);
    }
}
