package com.baeldung.java8;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.Assert.assertEquals;

public class UnsignedArithmeticUnitTest {
    @Test
    public void whenDoublingALargeByteNumber_thenOverflow() {
        byte b1 = 100;
        byte b2 = (byte) (b1 << 1);

        assertEquals(-56, b2);
    }

    @Test
    public void whenComparingNumbers_thenNegativeIsInterpretedAsUnsigned() {
        int positive = Integer.MAX_VALUE;
        int negative = Integer.MIN_VALUE;

        int signedComparison = Integer.compare(positive, negative);
        assertEquals(1, signedComparison);

        int unsignedComparison = Integer.compareUnsigned(positive, negative);
        assertEquals(-1, unsignedComparison);

        assertEquals(negative, positive + 1);
    }

    @Test
    public void whenDividingNumbers_thenNegativeIsInterpretedAsUnsigned() {
        int positive = Integer.MAX_VALUE;
        int negative = Integer.MIN_VALUE;

        assertEquals(-1, negative / positive);
        assertEquals(1, Integer.divideUnsigned(negative, positive));

        assertEquals(-1, negative % positive);
        assertEquals(1, Integer.remainderUnsigned(negative, positive));
    }

    @Test
    public void whenParsingNumbers_thenNegativeIsInterpretedAsUnsigned() {
        Throwable thrown = catchThrowable(() -> Integer.parseInt("2147483648"));
        assertThat(thrown).isInstanceOf(NumberFormatException.class);

        assertEquals(Integer.MAX_VALUE + 1, Integer.parseUnsignedInt("2147483648"));
    }

    @Test
    public void whenFormattingNumbers_thenNegativeIsInterpretedAsUnsigned() {
        String signedString = Integer.toString(Integer.MIN_VALUE);
        assertEquals("-2147483648", signedString);

        String unsignedString = Integer.toUnsignedString(Integer.MIN_VALUE);
        assertEquals("2147483648", unsignedString);
    }
}
