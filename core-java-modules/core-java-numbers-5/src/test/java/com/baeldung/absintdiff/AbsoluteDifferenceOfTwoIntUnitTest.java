package com.baeldung.absintdiff;

import static com.baeldung.absintdiff.IntDiffUtil.absDiff;
import static com.baeldung.absintdiff.IntDiffUtil.absDiff2;
import static com.baeldung.absintdiff.IntDiffUtil.absDiffAsLong;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class IntDiffUtil {
    static int absDiff(int num1, int num2) {
        int result = Math.abs(num1 - num2);
        System.out.println("Absolute diff: " + result);
        return result;
    }

    static int absDiff2(int num1, int num2) {
        return Math.abs(Math.subtractExact(num1, num2));
    }

    static long absDiffAsLong(int num1, int num2) {
        return Math.abs((long) num1 - num2);
    }
}

public class AbsoluteDifferenceOfTwoIntUnitTest {

    @Test
    void givenTwoIntegers_whenCallingAbsDiff_shouldGetExpectedResult() {
        int diff1 = absDiff(100, -200);
        assertEquals(300, diff1);

        int diff2 = absDiff(100, 200);
        assertEquals(100, diff2);

        //integer overflow!  output: Absolute diff: 2147483449
        //absDiff(Integer.MAX_VALUE, -200);
    }

    @Test
    void givenTwoIntegers_whenCallingAbsDiff2_shouldThrowException() {
        int diff1 = absDiff2(100, -200);
        assertEquals(300, diff1);

        int diff2 = absDiff2(100, 200);
        assertEquals(100, diff2);

        //overflow -> exception
        assertThrows(ArithmeticException.class, () -> absDiff2(Integer.MAX_VALUE, -200));
    }

    @Test
    void givenTwoIntegers_whenCallingAbsDiffAsLong_shouldThrowException() {
        long diff = absDiffAsLong(Integer.MAX_VALUE, -200);
        assertEquals(Integer.MAX_VALUE + 200L, diff);
    }
}