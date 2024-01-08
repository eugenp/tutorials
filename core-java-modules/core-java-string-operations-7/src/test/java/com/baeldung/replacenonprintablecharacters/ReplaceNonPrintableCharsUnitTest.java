package com.baeldung.replacenonprintablecharacters;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReplaceNonPrintableCharsUnitTest {
    @Test
    public void givenTextWithNonPrintableChars_whenUsingRegularExpression_thenGetSanitizedText() {
        String originalText = "\n\nWelcome \n\n\n\tto Baeldung!\n\t";
        String expected = "Welcome to Baeldung!";
        String regex = "[\\p{C}]";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(originalText);
        String sanitizedText = matcher.replaceAll("");

        assertEquals(expected, sanitizedText);
    }

    @Test
    public void givenTextWithNonPrintableChars_whenCustomImplementation_thenGetSanitizedText() {
        String originalText = "\n\nWelcome \n\n\n\tto Baeldung!\n\t";
        String expected = "Welcome to Baeldung!";

        StringBuilder strBuilder = new StringBuilder();
        originalText.codePoints().forEach((i) -> {
            if (i >= 32 && i != 127) {
                strBuilder.append(Character.toChars(i));
            }
        });

        assertEquals(expected, strBuilder.toString());
    }

}
