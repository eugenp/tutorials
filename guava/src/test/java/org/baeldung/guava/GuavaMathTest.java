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
    @Test(expected = ArithmeticException.class)
    public void when_sum_overflow_then_throw_exception() {
        IntMath.checkedAdd(Integer.MAX_VALUE, 1);
    }

    @Test(expected = ArithmeticException.class)
    public void when_sum_underflow_then_throw_exception() {
        IntMath.checkedAdd(Integer.MIN_VALUE, -1);
    }

    @Test
    public void should_calculate_sum() {
        int result = IntMath.checkedAdd(2, 1);
        assertThat(result, equalTo(3));
    }

    @Test
    public void when_sum_overflow_then_return_max_integer() {
        int result = IntMath.saturatedAdd(Integer.MAX_VALUE, 100);
        assertThat(result, equalTo(Integer.MAX_VALUE));
    }

    @Test
    public void when_sum_underflow_then_return_min_integer() {
        int result = IntMath.saturatedAdd(Integer.MIN_VALUE, -100);
        assertThat(result, equalTo(Integer.MIN_VALUE));
    }

    @Test(expected = ArithmeticException.class)
    public void when_difference_overflow_then_throw_exception() {
        IntMath.checkedSubtract(Integer.MAX_VALUE, -1);
    }

    @Test(expected = ArithmeticException.class)
    public void when_difference_underflow_then_throw_exception() {
        IntMath.checkedSubtract(Integer.MIN_VALUE, 1);
    }

    @Test
    public void should_calculate_difference() {
        int result = IntMath.checkedSubtract(200, 100);
        assertThat(result, equalTo(100));
    }

    @Test
    public void when_difference_overflow_then_return_max_integer() {
        int result = IntMath.saturatedSubtract(Integer.MAX_VALUE, -1);
        assertThat(result, equalTo(Integer.MAX_VALUE));
    }

    @Test
    public void when_difference_underflow_then_return_min_integer() {
        int result = IntMath.saturatedSubtract(Integer.MIN_VALUE, 1);
        assertThat(result, equalTo(Integer.MIN_VALUE));
    }

    @Test(expected = ArithmeticException.class)
    public void when_product_overflow_then_throw_exception() {
        IntMath.checkedMultiply(Integer.MAX_VALUE, 2);
    }

    @Test(expected = ArithmeticException.class)
    public void when_product_underflow_then_throw_exception() {
        IntMath.checkedMultiply(Integer.MIN_VALUE, 2);
    }

    @Test
    public void should_calculate_product() {
        int result = IntMath.checkedMultiply(21, 3);
        assertThat(result, equalTo(63));
    }

    @Test
    public void when_product_overflow_then_return_max_integer() {
        int result = IntMath.saturatedMultiply(Integer.MAX_VALUE, 2);
        assertThat(result, equalTo(Integer.MAX_VALUE));
    }

    @Test
    public void when_product_underflow_then_return_min_integer() {
        int result = IntMath.saturatedMultiply(Integer.MIN_VALUE, 2);
        assertThat(result, equalTo(Integer.MIN_VALUE));
    }

    @Test(expected = ArithmeticException.class)
    public void when_power_overflow_then_throw_exception() {
        IntMath.checkedPow(Integer.MAX_VALUE, 2);
    }

    @Test(expected = ArithmeticException.class)
    public void when_power_underflow_then_throw_exception() {
        IntMath.checkedPow(Integer.MIN_VALUE, 3);
    }

    @Test
    public void should_calculate_power() {
        int result = IntMath.saturatedPow(3, 3);
        assertThat(result, equalTo(27));
    }

    @Test
    public void when_power_overflow_then_return_max_integer() {
        int result = IntMath.saturatedPow(Integer.MAX_VALUE, 2);
        assertThat(result, equalTo(Integer.MAX_VALUE));
    }

    @Test
    public void when_power_underflow_then_return_min_integer() {
        int result = IntMath.saturatedPow(Integer.MIN_VALUE, 3);
        assertThat(result, equalTo(Integer.MIN_VALUE));
    }

    @Test
    public void should_round_to_integer() {
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
    }

    @Test(expected = ArithmeticException.class)
    public void when_need_rounding_then_throw_exception() {
        IntMath.sqrt(5, RoundingMode.UNNECESSARY);
    }

    @Test
    public void should_calculate_gcd() {
        int result = IntMath.gcd(15, 20);
        assertThat(result, equalTo(5));
    }

    @Test
    public void should_calculate_mod() {
        int result = IntMath.mod(8, 3);
        assertThat(result, equalTo(2));
    }

    @Test
    public void should_test_if_is_power_of_two() {
        boolean result1 = IntMath.isPowerOfTwo(8);
        assertTrue(result1);

        boolean result2 = IntMath.isPowerOfTwo(9);
        assertFalse(result2);
    }

    @Test
    public void should_calculate_factorial() {
        int result = IntMath.factorial(4);
        assertThat(result, equalTo(24));
    }

    @Test
    public void should_calculate_binomial() {
        int result = IntMath.binomial(7, 3);
        assertThat(result, equalTo(35));
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