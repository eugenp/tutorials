package com.baeldung.varargs;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HeapPollutionUnitTest {

    @Test(expected = ClassCastException.class)
    public void givenGenericVararg_whenUsedUnsafe_shouldThrowClassCastException() {
        String one = firstOfFirst(Arrays.asList("one", "two"), Collections.emptyList());

        assertEquals("one", one);
    }

    @Test(expected = ClassCastException.class)
    public void givenGenericVararg_whenRefEscapes_mayCauseSubtleBugs() {
        String[] args = returnAsIs("One", "Two");
    }

    private static String firstOfFirst(List<String>... strings) {
        List<Integer> ints = Collections.singletonList(42);
        Object[] objects = strings;
        objects[0] = ints;

        return strings[0].get(0);
    }

    private static <T> T[] toArray(T... arguments) {
        return arguments;
    }

    private static <T> T[] returnAsIs(T a, T b) {
        return toArray(a, b);
    }
}
