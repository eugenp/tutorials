package com.baeldung.stringandstringbuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ConvertBetweenStringAndStringBuilderUnitTest {

    @Test
    void whenConvertStringBuilderToString_thenCorrect() {
        StringBuilder sb = new StringBuilder("Converting SB to String");
        String result = sb.toString();
        assertEquals("Converting SB to String", result);

        result = sb.substring(11, 13);
        assertEquals("SB", result);
    }

    @Test
    void whenConvertStringToStringBuilderUsingConstructor_thenCorrect() {
        String input = "Converting String to SB";
        StringBuilder sb = new StringBuilder(input);
        assertEquals(input, sb.toString());
    }

    @Test
    void whenConvertStringToStringBuilderUsingAppend_thenCorrect() {
        String input = "C C++ C# Ruby Go Rust";
        StringBuilder sb = new StringBuilder().append(input);
        assertEquals(input, sb.toString());

        String[] strings = new String[] { "C ", "C++ ", "C# ", "Ruby ", "Go ", "Rust" };
        StringBuilder sb2 = new StringBuilder();
        for (String str : strings) {
            sb2.append(str);
        }
        assertEquals(input, sb2.toString());
    }

    @Test
    void whenConcatenateAndReverseUsingStringBuilder_thenCorrect() {
        String str1 = "c b a";
        String str2 = "^_*";
        String str3 = "z y x";

        StringBuilder sb = new StringBuilder(str1)
            .append(" | ")
            .append(str2)
            .append(" | ")
            .append(str3)
            .reverse();

        String result = sb.toString();
        assertEquals("x y z | *_^ | a b c", result);
    }
}