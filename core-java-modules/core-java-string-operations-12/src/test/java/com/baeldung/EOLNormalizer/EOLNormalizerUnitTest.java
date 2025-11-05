package com.baeldung.EOLNormalizer;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.storm.shade.org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class EOLNormalizerUnitTest {
    String originalText = "This is a text\rwith different\r\nEOL characters\n";
    String expectedText = "This is a text" + System.getProperty("line.separator") + "with different" + System.getProperty("line.separator") +
            "EOL characters" + System.getProperty("line.separator");

    @Test
    public void givenText_whenUsingStringReplace_thenEOLNormalized() {
        String normalizedText = originalText.replaceAll("\\r\\n|\\r|\\n", System.getProperty("line.separator"));
        assertEquals(expectedText, normalizedText);
    }

    @Test
    public void givenText_whenUsingStringUtils_thenEOLNormalized() {
        String normalizedText = StringUtils.replaceEach(originalText, new String[]{"\r\n", "\r", "\n"}, new String[]{System.getProperty("line.separator"), System.getProperty("line.separator"), System.getProperty("line.separator")});
        assertEquals(expectedText, normalizedText);
    }

    @Test
    public void givenText_whenUsingStreamAPI_thenEOLNormalized() {
        String normalizedText = Arrays.stream(originalText.split("\\r\\n|\\r|\\n"))
                .collect(Collectors.joining(System.getProperty("line.separator"))).trim();
        assertEquals(expectedText.trim(), normalizedText);
    }
}
