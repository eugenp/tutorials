package com.baeldung;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NewStringAPIUnitTest {

    @Test
    public void whenRepeatStringTwice_thenGetStringTwice() {
        String output = "La ".repeat(2) + "Land";

        is(output).equals("La La Land");
    }

    @Test
    public void whenStripString_thenReturnStringWithoutWhitespaces() {
        is("\n\t  hello   \u2005".strip()).equals("hello");
    }

    @Test
    public void whenTrimAdvanceString_thenReturnStringWithWhitespaces() {
        is("\n\t  hello   \u2005".trim()).equals("hello   \u2005");
    }

    @Test
    public void whenBlankString_thenReturnTrue() {
        assertTrue("\n\t\u2005  ".isBlank());
    }

    @Test
    public void whenMultilineString_thenReturnNonEmptyLineCount() {
        String multilineStr = "This is\n \n a multiline\n string.";

        long lineCount = multilineStr.lines()
          .filter(String::isBlank)
          .count();

        is(lineCount).equals(3L);
    }
}