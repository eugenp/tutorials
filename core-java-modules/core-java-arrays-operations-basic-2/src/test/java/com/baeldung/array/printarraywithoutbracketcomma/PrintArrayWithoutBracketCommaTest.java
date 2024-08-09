package com.baeldung.array.printarraywithoutbracketcomma;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import com.google.common.base.Joiner;

public class PrintArrayWithoutBracketCommaTest {

    String[] content = new String[] { "www.", "Baeldung.", "com" };

    @Test
    public void givenArray_whenUsingStringBuilder_thenPrintedArrayWithoutCommaBrackets() {
        StringBuilder builder = new StringBuilder();
        for (String element: content) {
            builder.append(element);
        }

        assertEquals("www.Baeldung.com", builder.toString());
    }

    @Test
    public void givenArray_whenUsingStringReplace_thenPrintedArrayWithoutCommaBrackets() {
        String result = Arrays.toString(content)
            .replace("[", "")
            .replace("]", "")
            .replace(", ", "");

        assertEquals("www.Baeldung.com", result);
    }

    @Test
    public void givenArray_whenUsingStringReplaceAll_thenPrintedArrayWithoutCommaBrackets() {
        String result = Arrays.toString(content).replaceAll("\\[|\\]|, ", "");

        assertEquals("www.Baeldung.com", result);
    }

    @Test
    public void givenArray_whenUsingStringJoin_thenPrintedArrayWithoutCommaBrackets() {
        String result = String.join("", content);

        assertEquals("www.Baeldung.com", result);
    }

    @Test
    public void givenArray_whenUsingStream_thenPrintedArrayWithoutCommaBrackets() {
        String result = Stream.of(content).collect(Collectors.joining(""));

        assertEquals("www.Baeldung.com", result);
    }

    @Test
    public void givenArray_whenUsingStringUtilsJoin_thenPrintedArrayWithoutCommaBrackets() {
        String result = StringUtils.join(content, "");

        assertEquals("www.Baeldung.com", result);
    }

    @Test
    public void givenArray_whenUsingJoinerJoin_thenPrintedArrayWithoutCommaBrackets() {
        String result = Joiner.on("").join(content);

        assertEquals("www.Baeldung.com", result);
    }
}