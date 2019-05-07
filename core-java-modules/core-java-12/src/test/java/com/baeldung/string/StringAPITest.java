package com.baeldung.string;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;

public class StringAPITest {

    @Test
    public void whenPositiveArgument_thenReturnIndentedString() {
        String multilineStr = "This is\na multiline\nstring.";
        String outputStr = "   This is\n   a multiline\n   string.\n";

        String postIndent = multilineStr.indent(3);

        assertThat(outputStr, equalTo(postIndent));
    }

    @Test
    public void whenNegativeArgument_thenReturnReducedIndentedString() {
        String multilineStr = "   This is\n   a multiline\n   string.";
        String outputStr = " This is\n a multiline\n string.\n";

        String postIndent = multilineStr.indent(-2);

        assertThat(outputStr, equalTo(postIndent));
    }

    @Test
    public void whenTransformUsingLamda_thenReturnTransformedString() {
        String result = "hello".transform(input -> input + " world!");

        assertThat("hello world!", equalTo(result));
    }

    @Test
    public void whenTransformUsingParseInt_thenReturnInt() {
        int result = "42".transform(Integer::parseInt);

        assertThat(42, equalTo(result));
    }
}
