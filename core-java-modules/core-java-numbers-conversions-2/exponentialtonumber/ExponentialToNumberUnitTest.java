package com.baeldung.exponentialtonumber;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.junit.jupiter.api.Test;

public class ExponentialToNumberUnitTest {

    String scientificValueStringPositive = "1.2345E3";
    String expectedValuePositive = "1234.5";
    String scientificValueStringNegative = "-1.2345E3";
    String expectedValueNegative = "-1234.5";

    @Test
    public void givenScientificValueString_whenUtilizingDecimalFormat_thenCorrectNumberFormat() {

        double scientificValuePositive = Double.parseDouble(scientificValueStringPositive);
        DecimalFormat decimalFormat = new DecimalFormat("0.######");
        String resultPositive = decimalFormat.format(scientificValuePositive);
        assertEquals(expectedValuePositive, resultPositive);

        double scientificValueNegative = Double.parseDouble(scientificValueStringNegative);
        String resultNegative = decimalFormat.format(scientificValueNegative);
        assertEquals(expectedValueNegative, resultNegative);
    }

    @Test
    public void givenScientificValueString_whenUtilizingBigDecimal_thenCorrectNumberFormat() {
        BigDecimal bigDecimalPositive = new BigDecimal(scientificValueStringPositive);
        String resultPositive = bigDecimalPositive.toPlainString();
        assertEquals(expectedValuePositive, resultPositive);

        BigDecimal bigDecimalNegative = new BigDecimal(scientificValueStringNegative);
        String resultNegative = bigDecimalNegative.toPlainString();
        assertEquals(expectedValueNegative, resultNegative);
    }

    @Test
    public void givenScientificValueString_whenUtilizingStringFormat_thenCorrectNumberFormat() {
        double scientificValuePositive = Double.parseDouble(scientificValueStringPositive);
        String formatResultPositive = String.format("%.6f", scientificValuePositive).replaceAll("0*$", "").replaceAll("\\.$", "");
        assertEquals(expectedValuePositive, formatResultPositive);

        double scientificValueNegative = Double.parseDouble(scientificValueStringNegative);
        String formatResultNegative = String.format("%.6f", scientificValueNegative).replaceAll("0*$", "").replaceAll("\\.$", "");
        assertEquals(expectedValueNegative, formatResultNegative);
    }
}
