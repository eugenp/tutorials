package com.baeldung.stringbuilderhaschar;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckIfStringBuilderContainsCharUnitTest {

    StringBuilder stringBuilder = new StringBuilder("Welcome to Baeldung Java Tutorial!");
    char targetChar = 'o';

    @Test
    public void givenStringBuilder_whenUsingIndexOfMethod_thenCheckIfSCharExists() {
        int index = stringBuilder.indexOf(String.valueOf(targetChar));
        assertTrue(index != -1);
    }

    @Test
    public void givenStringBuilder_whenUsingContainsMethod_thenCheckIfSCharExists() {
        boolean containsChar = stringBuilder.toString().contains(String.valueOf(targetChar));
        assertTrue(containsChar);
    }

    @Test
    public void givenStringBuilder_whenUsingJavaStream_thenCheckIfSCharExists() {
        boolean charFound = stringBuilder.chars().anyMatch(c -> c == targetChar);

        assertTrue(charFound);
    }

    @Test
    public void givenStringBuilder_whenUsingIterations_thenCheckIfSCharExists() {
        boolean charFound = false;

        for (int i = 0; i < stringBuilder.length(); i++) {
            if (stringBuilder.charAt(i) == targetChar) {
                charFound = true;
                break;
            }
        }

        assertTrue(charFound);
    }
}
