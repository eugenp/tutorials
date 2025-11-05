package com.baeldung.string.removedigits;

import org.apache.commons.lang3.RegExUtils;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RemoveDigitsUnitTest {

    static final String INPUT_STRING = "M3a9n2y n8u6m7b5e4r0s1";
    static final String EXPECTED_STRING = "Many numbers";
    static final String DIGIT_REGEX = "\\d";

    @Test
    void whenUsingReplaceAll_thenGetExpectedResult() {
        String updatedString = INPUT_STRING.replaceAll(DIGIT_REGEX, "");

        assertEquals(EXPECTED_STRING, updatedString);
    }

    @Test
    void whenUsingPatternAndMatcher_thenGetExpectedResult() {
        Pattern pattern = Pattern.compile(DIGIT_REGEX);
        Matcher matcher = pattern.matcher(INPUT_STRING);
        String updatedString = matcher.replaceAll("");

        assertEquals(EXPECTED_STRING, updatedString);
    }

    @Test
    void whenUsingApacheCommonsLang_thenGetExpectedResult() {
        String updatedString = RegExUtils.replacePattern(INPUT_STRING, DIGIT_REGEX, "");

        assertEquals(EXPECTED_STRING, updatedString);
    }

    @Test
    void whenUsingForLoop_thenGetExpectedResult() {
        StringBuilder sb = new StringBuilder();
        for (char ch : INPUT_STRING.toCharArray()) {
            if (!Character.isDigit(ch)) {
                sb.append(ch);
            }
        }

        assertEquals(EXPECTED_STRING, sb.toString());
    }

    @Test
    void whenUsingCharacterStream_thenGetExpectedResult() {
        String updatedString = INPUT_STRING.chars()
          .filter(c -> !Character.isDigit(c))
          .mapToObj(c -> (char) c)
          .map(String::valueOf)
          .collect(Collectors.joining());

        assertEquals(EXPECTED_STRING, updatedString);
    }

}
