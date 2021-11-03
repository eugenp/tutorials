package com.baeldung.firstword;

import static com.baeldung.firstword.FirstWordGetter.getFirstWordUsingSplit;
import static com.baeldung.firstword.FirstWordGetter.getFirstWordUsingSubString;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FirstWordGetterUnitTest {

    @Test
    public void givenString_whenSplit_thenFirstWordIsReturned() {
        assertEquals("Roberto", getFirstWordUsingSplit("Roberto \"I wish you a bug-free day\""));
    }

    @Test
    public void givenStringWithNoSpace_whenSplit_thenFirstWordIsReturned() {
        assertEquals("StringWithNoSpace", getFirstWordUsingSplit("StringWithNoSpace"));
    }

    @Test
    public void givenString_whenPassedToSubstring_thenFirstWordIsReturned() {
        assertEquals("Roberto", getFirstWordUsingSubString("Roberto \"I wish you a bug-free day\""));
    }

    @Test
    public void givenStringWithNoSpace_whenPassedToSubstring_thenFirstWordIsReturned() {
        assertEquals("", getFirstWordUsingSubString("StringWithNoSpace"));
    }
}
