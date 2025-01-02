package com.baeldung.functional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class FirstClassFunctionsUnitTest {

    @Test
    public void testSortingWithoutLambda() {

        assertEquals(Integer.valueOf(8),
            FirstClassFunctions.sortWithoutLambda(Arrays.asList(10, 8)).get(0));

    }

    @Test
    public void testSortingWithLambda() {

        assertEquals(Integer.valueOf(8),
            FirstClassFunctions.sortWithLambda(Arrays.asList(10, 8)).get(0));

    }

}
