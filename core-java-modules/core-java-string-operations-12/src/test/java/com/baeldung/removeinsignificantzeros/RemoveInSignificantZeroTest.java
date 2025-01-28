package com.baeldung.removeinsignificantzeros;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.junit.Test;

public class RemoveInSignificantZeroTest {

    @Test
    public void givenPositiveNumberString_whenUsingStringReplaceAll_thenInsignificantZeroRemoved() {
        String positiveNumber = "001200.35000";
        positiveNumber = positiveNumber.contains(".") ? positiveNumber.replaceAll("^(-?)0+(\\d+)", "$1$2")
            .replaceAll("0+$", "")
            .replaceAll("\\.$", ".0") : positiveNumber.replaceAll("^(-?)0+(\\d+)", "$1$2");

        assertEquals("1200.35", positiveNumber);
    }

    @Test
    public void givenNegativeNumberString_whenUsingStringReplaceAll_thenInsignificantZeroRemoved() {
        String negativeNumber = "-0015.052200";
        negativeNumber = negativeNumber.contains(".") ? negativeNumber.replaceAll("^(-?)0+(\\d+)", "$1$2")
            .replaceAll("0+$", "")
            .replaceAll("\\.$", ".0") : negativeNumber.replaceAll("^(-?)0+(\\d+)", "$1$2");

        assertEquals("-15.0522", negativeNumber);
    }

    @Test
    public void givenPositiveNumberString_whenUsingDecimalFormat_thenInsignificantZeroRemoved() {
        String positiveNumber = "001200.35000";
        positiveNumber = new DecimalFormat("0.0#####").format(Double.valueOf(positiveNumber));

        assertEquals("1200.35", positiveNumber);
    }

    @Test
    public void givenNegativeNumberString_whenUsingDecimalFormat_thenInsignificantZeroRemoved() {
        String negativeNumber = "-0015.052200";
        negativeNumber = new DecimalFormat("0.0#####").format(Double.valueOf(negativeNumber));

        assertEquals("-15.0522", negativeNumber);
    }

    @Test
    public void givenPositiveNumberString_whenUsingBigDecimal_thenInsignificantZeroRemoved() {
        String positiveNumber = "001200.35000";
        positiveNumber = new BigDecimal(positiveNumber).stripTrailingZeros()
            .toPlainString();

        assertEquals("1200.35", positiveNumber);
    }

    @Test
    public void givenNegativeNumberString_whenUsingBigDecimal_thenInsignificantZeroRemoved() {
        String negativeNumber = "-0015.052200";
        negativeNumber = new BigDecimal(negativeNumber).stripTrailingZeros()
            .toPlainString();

        assertEquals("-15.0522", negativeNumber);
    }

}
