package com.baeldung.exponentialtonumber;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExponentialToNumberUnitTest {
    double scientificValue = 1.23456789E9;
    String expected = "1234567890";

    @Test
    public void givenScientificValue_whenUtilizingDecimalFormat_thenCorrectNumberFormat() {
        DecimalFormat decimalFormat = new DecimalFormat("#.################");
        String result = decimalFormat.format(scientificValue);

        assertEquals(expected, result);
    }

    @Test
    public void givenScientificValue_whenUtilizingBigDecimal_thenCorrectNumberFormat() {
        BigDecimal bigDecimal = new BigDecimal(scientificValue);
        String result = bigDecimal.toPlainString();

        assertEquals(expected, result);
    }

    @Test
    public void givenScientificValue_whenUtilizingStringFormat_thenCorrectNumberFormat() {
        String result = String.format("%.0f", scientificValue);

        assertEquals(expected, result);
    }
}