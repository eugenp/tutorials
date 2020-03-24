package com.baeldung.removingdecimals;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests that demonstrate some different approaches for formatting a
 * floating-point value into a {@link String} while removing the decimal part.
 */
public class RemovingDecimalsUnitTest {
    private final double doubleValue = 345.56;

    @Test
    public void whenCastToInt_thenValueIsTruncated() {
        String truncated = String.valueOf((int) doubleValue);
        assertEquals("345", truncated);
    }

    @Test
    public void givenALargeDouble_whenCastToInt_thenValueIsNotTruncated() {
        double outOfIntRange = 6_000_000_000.56;
        String truncationAttempt = String.valueOf((int) outOfIntRange);
        assertNotEquals("6000000000", truncationAttempt);
    }

    @Test
    public void whenUsingStringFormat_thenValueIsRounded() {
        String rounded = String.format("%.0f", doubleValue);
        assertEquals("346", rounded);
    }

    @Test
    public void givenALargeDouble_whenUsingStringFormat_thenValueIsStillRounded() {
        double outOfIntRange = 6_000_000_000.56;
        String rounded = String.format("%.0f", outOfIntRange);
        assertEquals("6000000001", rounded);
    }

    @Test
    public void whenUsingNumberFormat_thenValueIsRounded() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(0);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        String rounded = nf.format(doubleValue);
        assertEquals("346", rounded);
    }

    @Test
    public void whenUsingNumberFormatWithFloor_thenValueIsTruncated() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(0);
        nf.setRoundingMode(RoundingMode.FLOOR);
        String truncated = nf.format(doubleValue);
        assertEquals("345", truncated);
    }

    @Test
    public void whenUsingDecimalFormat_thenValueIsRounded() {
        DecimalFormat df = new DecimalFormat("#,###");
        df.setRoundingMode(RoundingMode.HALF_UP);
        String rounded = df.format(doubleValue);
        assertEquals("346", rounded);
    }

    @Test
    public void whenUsingDecimalFormatWithFloor_thenValueIsTruncated() {
        DecimalFormat df = new DecimalFormat("#,###");
        df.setRoundingMode(RoundingMode.FLOOR);
        String truncated = df.format(doubleValue);
        assertEquals("345", truncated);
    }

    @Test
    public void whenUsingBigDecimalDoubleValue_thenValueIsTruncated() {
        BigDecimal big = new BigDecimal(doubleValue);
        big = big.setScale(0, RoundingMode.FLOOR);
        String truncated = big.toString();
        assertEquals("345", truncated);
    }

    @Test
    public void whenUsingBigDecimalDoubleValueWithHalfUp_thenValueIsRounded() {
        BigDecimal big = new BigDecimal(doubleValue);
        big = big.setScale(0, RoundingMode.HALF_UP);
        String truncated = big.toString();
        assertEquals("346", truncated);
    }
}
