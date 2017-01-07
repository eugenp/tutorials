package com.baeldung.java.conversion;

import static org.junit.Assert.*;
import org.junit.Test;

public class StringtoCharArray {

    @Test
    public void StringToCharArray() {
        String string = "baeldung";
        char[] charArray = { 'b', 'a', 'e', 'l', 'd', 'u', 'n', 'g' };
        assertArrayEquals(charArray, string.toCharArray());
    }

    @Test
    public void charArrayToString() {
        char[] charArray = { 'b', 'a', 'e', 'l', 'd', 'u', 'n', 'g' };
        String string = new String(charArray);
        String string2 = String.valueOf(charArray);
        String string3 = String.copyValueOf(charArray);
        assertTrue(string.equals("baeldung"));
        assertTrue(string2.equals("baeldung"));
        assertTrue(string3.equals("baeldung"));
    }
}
