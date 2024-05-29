package com.baeldung.exponentialtonumber;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.junit.jupiter.api.Test;

public class ExponentialToNumberUnitTest {

    double scientificValue = 1.23456789E9;
    double scientificValueNegative = 1.23456789E-9;
    String expectedPositive = "1234567890";
    String expectedNegative = "0.00000000123456789";

    @Test
    public void givenScientificValue_whenUtilizingDecimalFormat_thenCorrectNumberFormat() {
        DecimalFormat decimalFormatPositive = new DecimalFormat("#");

        DecimalFormat decimalFormatNegative = new DecimalFormat("0.#################");

        String resultPositive = decimalFormatPositive.format(scientificValue);
        String resultNegative = decimalFormatNegative.format(scientificValueNegative);

        assertEquals(expectedPositive, resultPositive);
        assertEquals(expectedNegative, resultNegative);
    }

    @Test
    public void givenScientificValue_whenUtilizingBigDecimal_thenCorrectNumberFormat() {
        BigDecimal bigDecimalPositive = new BigDecimal(Double.toString(scientificValue));
        BigDecimal bigDecimalNegative = new BigDecimal(Double.toString(scientificValueNegative));
        String resultPositive = bigDecimalPositive.toPlainString();
        String resultNegative = bigDecimalNegative.toPlainString();

        assertEquals(expectedPositive, resultPositive);
        assertEquals(expectedNegative, resultNegative);
    }

    @Test
    public void givenScientificValue_whenUtilizingStringFormat_thenCorrectNumberFormat() {
        String resultPositive = String.format("%.0f", scientificValue);
        String resultNegative = String.format("%.17f", scientificValueNegative);

        assertEquals(expectedPositive, resultPositive);
        assertEquals(expectedNegative, resultNegative);
    }
}
