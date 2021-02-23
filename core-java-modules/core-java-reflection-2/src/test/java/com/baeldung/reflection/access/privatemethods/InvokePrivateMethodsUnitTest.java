package com.baeldung.reflection.access.privatemethods;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvokePrivateMethodsUnitTest {

    private final long[] someLongArray = new long[] { 1L, 2L, 1L, 4L, 2L };

    @Test
    public void whenSearchingForLongValueInSubsequence_theCorrectIndexOfTheValueIsReturned() throws Exception {
        Method indexOfNonWhitespaceMethod = LongArrayUtil.class.getDeclaredMethod("indexOf", long[].class, long.class, int.class, int.class);
        indexOfNonWhitespaceMethod.setAccessible(true);

        assertEquals(2, indexOfNonWhitespaceMethod.invoke(LongArrayUtil.class, someLongArray, 1L, 1, someLongArray.length), "The index should be 2.");
    }

}
