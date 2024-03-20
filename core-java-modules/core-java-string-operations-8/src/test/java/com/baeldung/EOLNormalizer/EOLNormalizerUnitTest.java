package com.baeldung.EOLNormalizer;

import org.apache.storm.shade.org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class EOLNormalizerUnitTest {
    String originalText = "This is a text\r\nwith different\rEOL characters\n";
    String expectedText = "This is a text\nwith different\nEOL characters\n";

    @Test
    public void givenText_whenUsingStringReplace_thenEOLNormalized() {
        String normalizedText = originalText.replace("\r\n", "\n").replace("\r", "\n");
        assertEquals(expectedText, normalizedText);
    }

    @Test
    public void givenText_whenUsingStringUtils_thenEOLNormalized() {
        String normalizedText = StringUtils.replace(originalText, "\r\n", "\n");
        assertEquals(expectedText, normalizedText);
    }

    @Test
    public void givenText_whenUsingRegex_thenEOLNormalized() {
        String normalizedText = originalText.replaceAll("\\r\\n|\\r|\\n", "\n");
        assertEquals(expectedText, normalizedText);
    }

    @Test
    public void givenText_whenUsingStreamAPI_thenEOLNormalized() {
        String normalizedText = Arrays.stream(originalText.split("\\r\\n|\\r|\\n"))
                .collect(Collectors.joining("\n"));
        assertEquals(expectedText, normalizedText);
    }
}
