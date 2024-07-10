package com.baeldung.array.nullorempty;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class CheckArrayNullOrEmptyUnitTest {

    private final static String[] STR_ARRAY = new String[] { "a", "b", "c", "d" };
    private final static BigDecimal[] EMPTY_ARRAY = new BigDecimal[] {};
    private final static String[] NULL_ARRAY = null;
    // primitive array
    private final static int[] INT_ARRAY = new int[] { 1, 2, 3, 4 };

    public static <T> boolean isArrayNullOrEmpty(T[] theArray) {
        return theArray == null || theArray.length == 0;
    }

    public static boolean isArrayNullOrEmpty2(Object[] theArray) {
        return theArray == null || theArray.length == 0;
    }

    public static boolean isArrayNullOrEmpty(int[] theArray) {
        return theArray == null || theArray.length == 0;
    }

    @Test
    void whenUsingIsArrayNullOrEmpty_thenCorrect() {
        assertTrue(isArrayNullOrEmpty(NULL_ARRAY));
        assertTrue(isArrayNullOrEmpty(EMPTY_ARRAY));
        assertFalse(isArrayNullOrEmpty(STR_ARRAY));

        //int array uses isArrayNullOrEmpty(int[] theArray)
        assertFalse(isArrayNullOrEmpty(INT_ARRAY));
    }

    @Test
    void whenUsingIsArrayNullOrEmpty2_thenCorrect() {
        assertTrue(isArrayNullOrEmpty2(NULL_ARRAY));
        assertTrue(isArrayNullOrEmpty2(EMPTY_ARRAY));
        assertFalse(isArrayNullOrEmpty2(STR_ARRAY));

        //primitive array won't work:
        //assertFalse(isArrayNullOrEmpty2(INT_ARRAY));
    }

    @Test
    void whenUsingArrayUtils_thenCorrect() {
        assertTrue(ArrayUtils.isEmpty(NULL_ARRAY));
        assertTrue(ArrayUtils.isEmpty(EMPTY_ARRAY));
        assertFalse(ArrayUtils.isEmpty(STR_ARRAY));
        //primitive array
        assertFalse(ArrayUtils.isEmpty(INT_ARRAY));
    }
}