package com.baeldung.combiningcollections;

import static org.junit.Assert.*;
import org.junit.Test;

public class CombiningArraysUnitTest {
    private static final String first[] = {
            "One", 
            "Two", 
            "Three"
    };
    
    private static final String second[] = {
            "Four", 
            "Five", 
            "Six"
    };

    private static final String expected[] = {
            "One", 
            "Two", 
            "Three",
            "Four", 
            "Five", 
            "Six"
    };
    
    @Test
    public void givenTwoArrays_whenUsingNativeJava_thenArraysCombined() {
        assertArrayEquals(expected, CombiningArrays.usingNativeJava(first, second));
    }

    @Test
    public void givenTwoArrays_whenUsingObjectStreams_thenArraysCombined() {
        assertArrayEquals(expected, CombiningArrays.usingJava8ObjectStream(first, second));
    }

    @Test
    public void givenTwoArrays_whenUsingFlatMaps_thenArraysCombined() {
        assertArrayEquals(expected, CombiningArrays.usingJava8FlatMaps(first, second));
    }

    @Test
    public void givenTwoArrays_whenUsingApacheCommons_thenArraysCombined() {
        assertArrayEquals(expected, CombiningArrays.usingApacheCommons(first, second));
    }

    @Test
    public void givenTwoArrays_whenUsingGuava_thenArraysCombined() {
        assertArrayEquals(expected, CombiningArrays.usingGuava(first, second));
    }
}
