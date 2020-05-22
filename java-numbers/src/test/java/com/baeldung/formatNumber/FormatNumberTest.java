package com.baeldung.formatNumber;

import org.apache.commons.math3.util.Precision;
import org.decimal4j.util.DoubleRounder;
import org.junit.Assert;
import org.junit.Test;

public class FormatNumberTest {
        private double value = 2.03456d;
        private int places = 2;
        private double expected = 2.03d;

        @Test public void givenDecimalNumber_whenFormatNumberToNDecimalPlaces_thenGetExpectedResult() {
                double delta = 0.0d;
                Assert.assertEquals(expected, FormatNumber.withBigDecimal(value, places), delta);
                Assert.assertEquals(expected, FormatNumber.withDecimalFormat(value, places), delta);
                Assert.assertEquals(expected, FormatNumber.withMathRound(value, places), delta);

                places = 3;
                expected = 2.035d;

                Assert.assertEquals(expected, FormatNumber.withBigDecimal(value, places), delta);
                Assert.assertEquals(expected, FormatNumber.withDecimalFormat(value, places), delta);
                Assert.assertEquals(expected, FormatNumber.withMathRound(value, places), delta);

                value = 256.024d;
                places = 2;
                expected = 256.02d;

                Assert.assertEquals(expected, FormatNumber.withBigDecimal(value, places), delta);
                Assert.assertEquals(expected, FormatNumber.withDecimalFormat(value, places), delta);
                Assert.assertEquals(expected, FormatNumber.withMathRound(value, places), delta);

                value = 260.773d;
                places = 2;
                expected = 260.77d;

                Assert.assertEquals(expected, FormatNumber.withBigDecimal(value, places), delta);
                Assert.assertEquals(expected, FormatNumber.withDecimalFormat(value, places), delta);
                Assert.assertEquals(expected, FormatNumber.withMathRound(value, places), delta);
        }
}
