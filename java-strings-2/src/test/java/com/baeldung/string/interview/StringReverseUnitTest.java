package com.baeldung.string.interview;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringReverseUnitTest {
    @Test
    public void whenUsingInbuildMethods_thenStringReversed() {
        String reversed = new StringBuilder("baeldung").reverse().toString();
        assertEquals("gnudleab", reversed);
    }
}
