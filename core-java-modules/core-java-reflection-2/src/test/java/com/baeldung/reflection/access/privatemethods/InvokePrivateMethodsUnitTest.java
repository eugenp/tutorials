package com.baeldung.reflection.access.privatemethods;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvokePrivateMethodsUnitTest {

    private final long[] someLongArray = new long[] { 1L, 2L, 1L, 4L, 2L };

    @Test
    void whenSearchingForLongValueInSubsequenceUsingReflection_thenTheCorrectIndexOfTheValueIsReturned() throws Exception {
        Method indexOfMethod = LongArrayUtil.class.getDeclaredMethod("indexOf", long[].class, long.class, int.class, int.class);
        indexOfMethod.setAccessible(true);

        assertEquals(2, indexOfMethod.invoke(LongArrayUtil.class, someLongArray, 1L, 1, someLongArray.length), "The index should be 2.");
    }

    @Test
    void whenSearchingForLongValueInSubsequenceUsingSpring_thenTheCorrectIndexOfTheValueIsReturned() throws Exception {
        int indexOfSearchTarget = ReflectionTestUtils.invokeMethod(LongArrayUtil.class, "indexOf", someLongArray, 1L, 1, someLongArray.length);
        assertEquals(2, indexOfSearchTarget, "The index should be 2.");
    }

}
