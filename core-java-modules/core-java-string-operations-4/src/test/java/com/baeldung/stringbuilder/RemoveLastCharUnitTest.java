package com.baeldung.stringbuilder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RemoveLastCharUnitTest {

    private static final String STRING_WITH_CHAR = "Spring Boot,Quarkus,Micronaut,";
    private static final String STRING_WITHOUT_CHAR = "Spring Boot,Quarkus,Micronaut";

    @Test
    void givenCharAtEndOfString_whenRemoveLastCharWithDeleteLastChar_thenLastCharRemoved() {
        StringBuilder sb = RemoveLastChar.joinStringsWithLastCharAsDelimiter();
        Assertions.assertEquals(STRING_WITH_CHAR, sb.toString());

        RemoveLastChar.removeLastCharWithDeleteLastChar(sb);
        Assertions.assertEquals(STRING_WITHOUT_CHAR, sb.toString());
    }

    @Test
    void givenCharAtEndOfString_whenRemoveLastCharWithSetLength_thenLastCharRemoved() {
        StringBuilder sb = RemoveLastChar.joinStringsWithLastCharAsDelimiter();
        Assertions.assertEquals(STRING_WITH_CHAR, sb.toString());

        RemoveLastChar.removeLastCharWithSetLength(sb);
        Assertions.assertEquals(STRING_WITHOUT_CHAR, sb.toString());
    }

    @Test
    void whenForLoopJoinWithPrefix_thenLastCharIsNotPresent() {
        Assertions.assertEquals(STRING_WITHOUT_CHAR, RemoveLastChar.joinStringsWithoutLastCharAsDelimiter()
            .toString());
    }

    @Test
    void whenJoiningStringsUsingJoin_thenLastCharIsNotPresent() {
        Assertions.assertEquals(STRING_WITHOUT_CHAR, RemoveLastChar.joinStringsUsingJoin());
    }

    @Test
    void whenJoiningStringsUsingStringJoiner_thenLastCharIsNotPresent() {
        Assertions.assertEquals(STRING_WITHOUT_CHAR, RemoveLastChar.joinUsingStringJoiner());
    }

    @Test
    void whenJoiningStringsUsingStringUtils_thenLastCharIsNotPresent() {
        Assertions.assertEquals(STRING_WITHOUT_CHAR, RemoveLastChar.joinUsingStringUtils());
    }
}
