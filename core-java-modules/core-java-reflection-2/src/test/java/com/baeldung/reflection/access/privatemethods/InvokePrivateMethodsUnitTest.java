package com.baeldung.reflection.access.privatemethods;

import com.google.common.primitives.Longs;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvokePrivateMethodsUnitTest {

    private final long[] someLongArray = new long[] { 1L, 2L, 3L, 4L, 5L };

    @Test
    public void whenSearchingForLongValue_theCorrectIndexOfTheValueIsReturned() throws Exception {
        Method indexOfNonWhitespace = Longs.class.getDeclaredMethod("indexOf", long[].class, long.class, int.class, int.class);
        indexOfNonWhitespace.setAccessible(true);

        assertEquals(1, indexOfNonWhitespace.invoke(Longs.class, someLongArray, 2L, 0, someLongArray.length), "The index should be 1.");
    }

}
