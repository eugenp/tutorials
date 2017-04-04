package org.baeldung.guava;

import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigInteger;
import java.math.RoundingMode;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class GuavaMathTest {
    @Test
    public void testIntMathAdd() {
        try {
            IntMath.checkedAdd(Integer.MAX_VALUE, 1);
            assertTrue(false);
        } catch (ArithmeticException e) {
            assertTrue(true);
        }

        try {
            IntMath.checkedAdd(Integer.MIN_VALUE, -1);
            assertTrue(false);
        } catch (ArithmeticException e) {
            assertTrue(true);
        }

        int result1 = IntMath.checkedAdd(2, 1);
        assertThat(result1, equalTo(3));

        int result2 = IntMath.saturatedAdd(Integer.MAX_VALUE, 100);
        assertThat(result2, equalTo(Integer.MAX_VALUE));

        int result3 = IntMath.saturatedAdd(Integer.MIN_VALUE, -100);
        assertThat(result3, equalTo(Integer.MIN_VALUE));
    }

    @Test
    public void testIntMathSubtract() {
        try {
            IntMath.checkedSubtract(Integer.MIN_VALUE, 1);
            assertTrue(false);
        } catch (ArithmeticException e) {
            assertTrue(true);
        }

        try {
            IntMath.checkedSubtract(Integer.MAX_VALUE, -1);
            assertTrue(false);
        } catch (ArithmeticException e) {
            assertTrue(true);
        };

        int result1 = IntMath.checkedSubtract(200, 100);
        assertThat(result1, equalTo(100));

        int result2 = IntMath.saturatedSubtract(Integer.MIN_VALUE, 1);
        assertThat(result2, equalTo(Integer.MIN_VALUE));

        int result3 = IntMath.saturatedSubtract(Integer.MAX_VALUE, -1);
        assertThat(result3, equalTo(Integer.MAX_VALUE));
    }

    @Test
    public void testIntMathMultiply() {
        try {
            IntMath.checkedMultiply(Integer.MAX_VALUE, 2);
            assertTrue(false);
        } catch (ArithmeticException e) {
            assertTrue(true);
        }

        try {
            IntMath.checkedMultiply(Integer.MIN_VALUE, 2);
            assertTrue(false);
        } catch (ArithmeticException e) {
            assertTrue(true);
        }

        int result1 = IntMath.checkedMultiply(21, 3);
        assertThat(result1, equalTo(63));

        int result2 = IntMath.saturatedMultiply(Integer.MAX_VALUE, 2);
        assertThat(result2, equalTo(Integer.MAX_VALUE));

        int result3 = IntMath.saturatedMultiply(Integer.MIN_VALUE, 2);
        assertThat(result3, equalTo(Integer.MIN_VALUE));
    }

    @Test
    public void testIntMathPow() {
        try {
            IntMath.checkedPow(Integer.MAX_VALUE, 2);
            assertTrue(false);
        } catch (ArithmeticException e) {
            assertTrue(true);
        }

        try {
            IntMath.checkedPow(Integer.MIN_VALUE, 3);
            assertTrue(false);
        } catch (ArithmeticException e) {
            assertTrue(true);
        }

        int result1 = IntMath.saturatedPow(3, 3);
        assertThat(result1, equalTo(27));

        int result2 = IntMath.saturatedPow(Integer.MAX_VALUE, 2);
        assertThat(result2, equalTo(Integer.MAX_VALUE));

        int result3 = IntMath.saturatedPow(Integer.MIN_VALUE, 3);
        assertThat(result3, equalTo(Integer.MIN_VALUE));
    }

    @Test
    public void testIntMathRound() {
        int result1 = IntMath.divide(3, 2, RoundingMode.DOWN);
        assertThat(result1, equalTo(1));
        int result2 = IntMath.divide(3, 2, RoundingMode.UP);
        assertThat(result2, equalTo(2));

        int result3 = IntMath.log2(5, RoundingMode.FLOOR);
        assertThat(result3, equalTo(2));
        int result4 = IntMath.log2(5, RoundingMode.CEILING);
        assertThat(result4, equalTo(3));

        int result5 = IntMath.log10(11, RoundingMode.HALF_UP);
        assertThat(result5, equalTo(1));

        int result6 = IntMath.sqrt(4, RoundingMode.UNNECESSARY);
        assertThat(result6, equalTo(2));
        try {
            IntMath.sqrt(5, RoundingMode.UNNECESSARY);
            assertTrue(false);
        } catch (ArithmeticException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testIntMathAdditionalFunctions() {
        int result1 = IntMath.gcd(15, 20);
        assertThat(result1, equalTo(5));

        int result2 = IntMath.mod(8, 3);
        assertThat(result2, equalTo(2));

        boolean result3 = IntMath.isPowerOfTwo(8);
        assertTrue(result3);
        boolean result4 = IntMath.isPowerOfTwo(9);
        assertFalse(result4);

        int result5 = IntMath.factorial(4);
        assertThat(result5, equalTo(24));

        int result6 = IntMath.binomial(7, 3);
        assertThat(result6, equalTo(35));
    }

    @Test
    public void should_detect_integer() {
        boolean result1 = DoubleMath.isMathematicalInteger(2.0);
        assertThat(result1, equalTo(true));
        boolean result2 = DoubleMath.isMathematicalInteger(2.1);
        assertThat(result2, equalTo(false));
    }

    @Test
    public void should_round_to_integer_types() {
        int result3 = DoubleMath.roundToInt(2.5, RoundingMode.DOWN);
        assertThat(result3, equalTo(2));

        long result4 = DoubleMath.roundToLong(2.5, RoundingMode.HALF_UP);
        assertThat(result4, equalTo(3L));

        BigInteger result5 = DoubleMath.roundToBigInteger(2.5, RoundingMode.UP);
        assertThat(result5, equalTo(new BigInteger("3")));
    }

    @Test
    public void should_calculate_log_2() {
        int result6 = DoubleMath.log2(10, RoundingMode.UP);
        assertThat(result6, equalTo(4));
    }
}