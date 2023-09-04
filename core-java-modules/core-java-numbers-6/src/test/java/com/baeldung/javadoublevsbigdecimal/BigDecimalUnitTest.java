package com.baeldung.javadoublevsbigdecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BigDecimalUnitTest {
    private BigDecimal bd1;
    private BigDecimal bd2;
    private BigDecimal expected;
    private BigDecimal actual;

    @BeforeEach
    public void setup() {
        bd1 = new BigDecimal("124567890.0987654321");
        bd2 = new BigDecimal("987654321.123456789");
    }

    @Test
    public void givenTwoBigDecimals_whenAdd_thenCorrect() {
        expected = new BigDecimal("1112222211.2222222211");
        actual = bd1.add(bd2);
        assertEquals(expected, actual);
    }

    @Test
    public void givenTwoBigDecimals_whenMultiply_thenCorrect() {
        expected = new BigDecimal("123030014929277547.5030955772112635269");
        actual = bd1.multiply(bd2);
        assertEquals(expected, actual);
    }

    @Test
    public void givenTwoBigDecimals_whenSubtract_thenCorrect() {
        expected = new BigDecimal("-863086431.0246913569");
        actual = bd1.subtract(bd2);
        assertEquals(expected, actual);
    }

    @Test
    public void givenTwoBigDecimals_whenDivide_thenCorrect() {
        expected = new BigDecimal("0.13");
        actual = bd1.divide(bd2, 2, RoundingMode.HALF_UP);
        assertEquals(expected, actual);
    }
}
