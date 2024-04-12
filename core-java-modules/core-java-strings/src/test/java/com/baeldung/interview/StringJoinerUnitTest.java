package com.baeldung.interview;

import org.junit.Test;

import java.util.StringJoiner;

import static org.junit.Assert.assertEquals;

public class StringJoinerUnitTest {
    @Test
    public void whenUsingStringJoiner_thenStringsJoined() {
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        joiner.add("Red")
          .add("Green")
          .add("Blue");
         
        assertEquals(joiner.toString(), "[Red,Green,Blue]");
    }
}
