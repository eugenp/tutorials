package com.baeldung.string.interview;

import java.util.StringJoiner;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

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
