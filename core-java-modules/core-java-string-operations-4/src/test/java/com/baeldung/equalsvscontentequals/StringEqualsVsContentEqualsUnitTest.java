package com.baeldung.equalsvscontentequals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StringEqualsVsContentEqualsUnitTest {

    String actualString = "baeldung";
    String stringInUpperCase = "BAELDUNG";
    String stringInWrongSequence = "baledung";
    String identicalString = "baeldung";
    StringBuffer buffer = new StringBuffer("baeldung");
    CharSequence charSequence = new StringBuffer("baeldung");

    @Test
    public void whenTestStringInUpperCase_thenBothFalse() {
        assertFalse(actualString.equals(stringInUpperCase));
        assertFalse(actualString.contentEquals(stringInWrongSequence));
    }

    @Test
    public void whenTestStringSequenceWrong_thenBothFalse() {
        assertFalse(actualString.equals(stringInWrongSequence));
        assertFalse(actualString.contentEquals(stringInWrongSequence));
    }

    @Test
    public void whenIdenticalTestString_thenBothTrue() {
        assertTrue(actualString.equals(identicalString));
        assertTrue(actualString.contentEquals(identicalString));
    }

    @Test
    public void whenSameContentButDifferentType_thenEqualsIsFalseAndContentEqualsIsTrue() {
        assertFalse(actualString.equals(buffer));
        assertFalse(actualString.equals(charSequence));

        assertTrue(actualString.contentEquals(buffer));
        assertTrue(actualString.contentEquals(charSequence));
    }

}
