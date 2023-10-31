package com.baeldung.truncatedouble;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.junit.Test;

public class TruncateDoubleUnitTest {

    @Test
    public void givenADouble_whenUsingDecimalFormat_truncateToTwoDecimalPlaces() {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.DOWN);

        double value = 1.55555555;
        String truncated = df.format(value);
        assertEquals("1.55", truncated);

        double negativeValue = -1.55555555;
        String negativeTruncated = df.format(negativeValue);
        assertEquals("-1.55", negativeTruncated);
    }

    @Test
    public void givenADouble_whenUsingNumberFormat_truncateToTwoDecimalPlaces() {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        nf.setRoundingMode(RoundingMode.DOWN);

        double value = 1.55555555;
        String truncated = nf.format(value);
        assertEquals("1.55", truncated);

        double negativeValue = -1.55555555;
        String negativeTruncated = nf.format(negativeValue);
        assertEquals("-1.55", negativeTruncated);
    }

    @Test
    public void givenADouble_whenUsingBigDecimal_truncateToTwoDecimalPlaces() {
        BigDecimal positive = new BigDecimal(2.555555).setScale(2, RoundingMode.DOWN);
        BigDecimal negative = new BigDecimal(-2.555555).setScale(2, RoundingMode.DOWN);
        assertEquals("2.55", positive.toString());
        assertEquals("-2.55", negative.toString());
    }

    @Test
    public void givenADouble_whenUsingMath_truncateToTwoDecimalPlaces() {
        double positive = 1.55555555;
        double truncated = Math.floor(positive * 100) / 100;
        assertEquals("1.55", String.valueOf(truncated));

        double negative = -1.55555555;
        double negativeTruncated = Math.ceil(negative * 100) / 100;
        assertEquals("-1.55", String.valueOf(negativeTruncated));
    }

    @Test
    public void givenADouble_whenUsingStringFormat_truncateToTwoDecimalPlaces() {
        double positive = 1.55555555;
        String truncated = String.format("%.2f", positive);
        assertEquals("1.56", truncated);

        double negative = -1.55555555;
        String negativeTruncated = String.format("%.2f", negative);
        assertEquals("-1.56", negativeTruncated);
    }

}
