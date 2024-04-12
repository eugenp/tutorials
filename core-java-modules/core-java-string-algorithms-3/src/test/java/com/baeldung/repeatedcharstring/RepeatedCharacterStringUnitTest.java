package com.baeldung.repeatedcharstring;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import static java.util.stream.Stream.generate;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RepeatedCharacterStringUnitTest {

    private static final String EXPECTED_STRING = "aaaaaaa";
    private static final int N = 7;

    @Test
    void givenSingleCharacterString_whenRepeat_thenStringCreated() {
        String newString = "a".repeat(N);
        assertEquals(EXPECTED_STRING, newString);
    }

    @Test
    void givenMultiCharacterString_whenRepeat_thenStringCreated() {
        String newString = "-->".repeat(5);
        assertEquals("-->-->-->-->-->", newString);
    }

    @Test
    void givenString_whenStringBuilderUsed_thenStringCreated() {
        StringBuilder builder = new StringBuilder(N);
        for (int i = 0; i < N; i++) {
            builder.append("a");
        }
        String newString = builder.toString();
        assertEquals(EXPECTED_STRING, newString);
    }

    @Test
    void givenString_whenCharArrayUsed_thenStringCreated() {
        char[] charArray = new char[N];
        for (int i = 0; i < N; i++) {
            charArray[i] = 'a';
        }
        String newString = new String(charArray);
        assertEquals(EXPECTED_STRING, newString);
    }

    @Test
    void givenString_whenApacheStringUtilsUsed_thenStringCreated() {
        char charToAppend = 'a';
        String newString = StringUtils.repeat(charToAppend, N);
        assertEquals(EXPECTED_STRING, newString);
    }

    @Test
    void givenString_whenGuavaRepeatUsed_thenStringCreated() {
        String charToAppend = "a";
        String newString = Strings.repeat(charToAppend, N);
        assertEquals(EXPECTED_STRING, newString);
    }

    @Test
    void givenString_whenArraysFillUsed_thenStringCreated() {
        char charToAppend = 'a';
        char[] charArray = new char[N];
        Arrays.fill(charArray, charToAppend);
        String newString = new String(charArray);
        assertEquals(EXPECTED_STRING, newString);
    }

    @Test
    void givenString_whenGenerateAndJoiningUsed_thenStringCreated() {
        String charToAppend = "a";
        String newString = generate(() -> charToAppend).limit(N).collect(Collectors.joining());
        assertEquals(EXPECTED_STRING, newString);
    }

    @Test
    void givenString_whenStringJoinUsed_thenStringCreated() {
        String charToAppend = "a";
        String newString = String.join("", Collections.nCopies(N, charToAppend));
        assertEquals(EXPECTED_STRING, newString);
    }

    @Test
    void givenString_whenGuavaJoinerUsed_thenStringCreated() {
        String charToAppend = "a";
        String newString = Joiner.on("").join(Collections.nCopies(N, charToAppend));
        assertEquals(EXPECTED_STRING, newString);
    }

    @Test
    void givenString_whenRandomStringUtilsUsed_thenStringCreated() {
        String charToAppend = "a";
        String newString = RandomStringUtils.random(N, charToAppend);
        assertEquals(EXPECTED_STRING, newString);
    }
}
