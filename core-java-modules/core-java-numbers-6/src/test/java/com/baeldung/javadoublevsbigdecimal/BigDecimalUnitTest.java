package com.baeldung.javadoublevsbigdecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.Test;

public class BigDecimalUnitTest {

    private BigDecimal bigDecimal1 = new BigDecimal("124567890.0987654321");
    private BigDecimal bigDecimal2 = new BigDecimal("987654321.123456789");

    @Test
    public void givenTwoBigDecimals_whenAdd_thenCorrect() {
        BigDecimal expected = new BigDecimal("1112222211.2222222211");
        BigDecimal actual = bigDecimal1.add(bigDecimal2);
        assertEquals(expected, actual);
    }

    @Test
    public void givenTwoBigDecimals_whenMultiply_thenCorrect() {
        BigDecimal expected = new BigDecimal("123030014929277547.5030955772112635269");
        BigDecimal actual = bigDecimal1.multiply(bigDecimal2);
        assertEquals(expected, actual);
    }

    @Test
    public void givenTwoBigDecimals_whenSubtract_thenCorrect() {
        BigDecimal expected = new BigDecimal("-863086431.0246913569");
        BigDecimal actual = bigDecimal1.subtract(bigDecimal2);
        assertEquals(expected, actual);
    }

    @Test
    public void givenTwoBigDecimals_whenDivide_thenCorrect() {
        BigDecimal expected = new BigDecimal("0.13");
        BigDecimal actual = bigDecimal1.divide(bigDecimal2, 2, RoundingMode.HALF_UP);
        assertEquals(expected, actual);
    }
}
