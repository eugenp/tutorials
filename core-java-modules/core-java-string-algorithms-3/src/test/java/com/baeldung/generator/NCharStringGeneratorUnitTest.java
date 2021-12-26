package com.baeldung.generator;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import static ch.qos.logback.core.CoreConstants.EMPTY_STRING;
import static java.util.stream.Stream.generate;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NCharStringGeneratorUnitTest {

    private static final String EXPECTED_STRING = "aaaaaaa";
    private static final int N = 7;

    @Test
    void givenString_whenStringRepeatUsed_thenStringCreated() {
        char charToAppend = 'a';
        String newString = String.valueOf(charToAppend).repeat(N);
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
        String newString = String.join(EMPTY_STRING, Collections.nCopies(N, charToAppend));
        assertEquals(EXPECTED_STRING, newString);
    }

    @Test
    void givenString_whenGuavaJoinerUsed_thenStringCreated() {
        String charToAppend = "a";
        String newString = Joiner.on(EMPTY_STRING).join(Collections.nCopies(N, charToAppend));
        assertEquals(EXPECTED_STRING, newString);
    }

    @Test
    void givenString_whenRandomStringUtilsUsed_thenStringCreated() {
        String charToAppend = "a";
        String newString = RandomStringUtils.random(N, charToAppend);
        assertEquals(EXPECTED_STRING, newString);
    }
}
