package com.baeldung.functional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class PureFunctionsUnitTets {

    @Test
    public void testSortingWithoutLambda() {

        assertEquals(new Integer(18), PureFunctions.sum(Arrays.asList(new Integer(10), new Integer(8))));

    }

}
