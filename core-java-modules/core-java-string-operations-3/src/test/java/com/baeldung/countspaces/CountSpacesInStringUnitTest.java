package com.baeldung.countspaces;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

class CountSpacesInStringUnitTest {
    private static final String INPUT_STRING = "  This string has nine spaces and a Tab:'	'";
    private static final int EXPECTED_COUNT = 9;

    @Test
    void givenString_whenCountSpaceByLooping_thenReturnsExpectedCount() {
        int spaceCount = 0;
        for (char c : INPUT_STRING.toCharArray()) {
            if (c == ' ') {
                spaceCount++;
            }
        }
        assertThat(spaceCount).isEqualTo(EXPECTED_COUNT);
    }

    @Test
    void givenString_whenCountSpaceByJava8StreamFilter_thenReturnsExpectedCount() {
        long spaceCount = INPUT_STRING.chars().filter(c -> c == (int) ' ').count();
        assertThat(spaceCount).isEqualTo(EXPECTED_COUNT);
    }

    @Test
    void givenString_whenCountSpaceByRegexMatcher_thenReturnsExpectedCount() {
        Pattern pattern = Pattern.compile(" ");
        Matcher matcher = pattern.matcher(INPUT_STRING);
        int spaceCount = 0;
        while (matcher.find()) {
            spaceCount++;
        }
        assertThat(spaceCount).isEqualTo(EXPECTED_COUNT);
    }

    @Test
    void givenString_whenCountSpaceByReplaceAll_thenReturnsExpectedCount() {
        int spaceCount = INPUT_STRING.replaceAll("[^ ]", "").length();
        assertThat(spaceCount).isEqualTo(EXPECTED_COUNT);
    }

    @Test
    void givenString_whenCountSpaceBySplit_thenReturnsExpectedCount() {
        int spaceCount = INPUT_STRING.split(" ").length - 1;
        assertThat(spaceCount).isEqualTo(EXPECTED_COUNT);
    }

    @Test
    void givenString_whenCountSpaceUsingApacheCommons_thenReturnsExpectedCount() {
        int spaceCount = StringUtils.countMatches(INPUT_STRING, " ");
        assertThat(spaceCount).isEqualTo(EXPECTED_COUNT);
    }

    @Test
    void givenString_whenCountSpaceUsingSpring_thenReturnsExpectedCount() {
        int spaceCount = org.springframework.util.StringUtils.countOccurrencesOf(INPUT_STRING, " ");
        assertThat(spaceCount).isEqualTo(EXPECTED_COUNT);
    }
}
