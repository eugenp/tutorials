package com.baeldung.repeatedcharstring;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import static ch.qos.logback.core.CoreConstants.EMPTY_STRING;
import static java.util.stream.Stream.generate;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RepeatedCharacterStringUnitTest {

    @Test
    public void givenString_whenStringBufferUsed_thenStringCreated() {
        String exampleString = "aaaaaaa";
        int length = 7;
        char charToAppend = 'a';
        StringBuilder outputBuffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            outputBuffer.append(charToAppend);
        }
        Assertions.assertEquals(exampleString, outputBuffer.toString());
    }

    @Test
    public void givenString_whenEmptyArrayUsed_thenStringCreated() {
        String exampleString = "aaaaaaa";
        int length = 7;
        char charToAppend = 'a';
        String newString = new String(new char[length]).replace('\0', charToAppend);
        Assertions.assertEquals(exampleString, newString);
    }

    @Test
    public void givenString_whenCharBufferUsed_thenStringCreated() {
        String exampleString = "aaaaaaa";
        int length = 7;
        char charToAppend = 'a';
        String newString = CharBuffer.allocate(length).toString().replace('\0', charToAppend);
        Assertions.assertEquals(exampleString, newString);
    }

    @Test
    public void givenString_whenApacheStringUtilsUsed_thenStringCreated() {
        String exampleString = "aaaaaaa";
        int length = 7;
        char charToAppend = 'a';
        String newString = StringUtils.repeat(charToAppend, length);
        Assertions.assertEquals(exampleString, newString);
    }

    @Test
    public void givenString_whenRepeatUsed_thenStringCreated() {
        String exampleString = "aaaaaaa";
        int length = 7;
        String charToAppend = "a";
        String newString = charToAppend.repeat(length);
        Assertions.assertEquals(exampleString, newString);
    }

    @Test
    public void givenString_whenGuavaRepeatUsed_thenStringCreated() {
        String exampleString = "aaaaaaa";
        int length = 7;
        String charToAppend = "a";
        String newString = Strings.repeat(charToAppend, length);
        Assertions.assertEquals(exampleString, newString);
    }

    @Test
    public void givenString_whenArraysFillUsed_thenStringCreated() {
        String exampleString = "aaaaaaa";
        int length = 7;
        char charToAppend = 'a';
        char[] charArray = new char[length];
        Arrays.fill(charArray, charToAppend);
        String newString = new String(charArray);
        Assertions.assertEquals(exampleString, newString);
    }

    @Test
    public void givenString_whenGenerateAndJoiningUsed_thenStringCreated() {
        String exampleString = "aaaaaaa";
        int length = 7;
        String charToAppend = "a";
        String newString = generate(() -> charToAppend).limit(length).collect(Collectors.joining());
        Assertions.assertEquals(exampleString, newString);
    }

    @Test
    public void givenString_whenStringJoinUsed_thenStringCreated() {
        String exampleString = "aaaaaaa";
        int length = 7;
        String charToAppend = "a";
        String newString = String.join(EMPTY_STRING, Collections.nCopies(length, charToAppend));
        Assertions.assertEquals(exampleString, newString);
    }

    @Test
    public void givenString_whenGuavaJoinerUsed_thenStringCreated() {
        String exampleString = "aaaaaaa";
        int length = 7;
        String charToAppend = "a";
        String newString = Joiner.on(EMPTY_STRING).join(Collections.nCopies(length, charToAppend)));
        Assertions.assertEquals(exampleString, newString);
    }

    @Test
    public void givenString_whenStringFormatUsed_thenStringCreated() {
        String exampleString = "aaaaaaa";
        int length = 7;
        String charToAppend = "a";
        String newString = String.format("%0" + length + "d", 0).replace("0", charToAppend);
        Assertions.assertEquals(exampleString, newString);
    }

    @Test
    public void givenString_whenCollectUsed_thenStringCreated() {
        String exampleString = "aaaaaaa";
        int length = 7;
        String charToAppend = "a";
        String newString = IntStream.range(0, length).mapToObj(i -> charToAppend).collect(Collectors.joining(EMPTY_STRING));
        Assertions.assertEquals(exampleString, newString);
    }

    @Test
    public void givenString_whenRandomStringUtilsUsed_thenStringCreated() {
        String exampleString = "aaaaaaa";
        int length = 7;
        String charToAppend = "a";
        String newString = RandomStringUtils.random(length, charToAppend);
        Assertions.assertEquals(exampleString, newString);
    }
}
