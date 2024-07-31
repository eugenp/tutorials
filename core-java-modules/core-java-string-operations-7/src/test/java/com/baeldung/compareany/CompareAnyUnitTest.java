package com.baeldung.compareany;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class CompareAnyUnitTest {

    @Test
    void givenStrings_whenCompareWithMultipleStringsUsingIf_thenSuccess() {
        String presentString = "Apple";
        String notPresentString = "Avocado";

        assertTrue(compareWithMultipleStringsUsingIf(presentString, "Mango", "Papaya", "Pineapple", "Apple"));
        assertFalse(compareWithMultipleStringsUsingIf(notPresentString, "Mango", "Papaya", "Pineapple", "Apple"));
    }

    @Test
    void givenStrings_whenCompareWithMultipleStringsUsingArrayUtils_thenSuccess() {
        String presentString = "Apple";
        String notPresentString = "Avocado";

        assertTrue(compareWithMultipleStringsUsingArrayUtils(presentString, "Mango", "Papaya", "Pineapple", "Apple"));
        assertFalse(compareWithMultipleStringsUsingArrayUtils(notPresentString, "Mango", "Papaya", "Pineapple", "Apple"));
    }

    @Test
    void givenStrings_whenCompareWithMultipleStringsUsingStringUtils_thenSuccess() {
        String presentString = "Apple";
        String notPresentString = "Avocado";

        assertTrue(compareWithMultipleStringsUsingStringUtils(presentString, "Mango", "Papaya", "Pineapple", "Apple"));
        assertFalse(compareWithMultipleStringsUsingStringUtils(notPresentString, "Mango", "Papaya", "Pineapple", "Apple"));
    }

    @Test
    void givenStrings_whenCompareCaseInsensitiveWithMultipleStringsUsingStringUtils_thenSuccess() {
        String presentString = "APPLE";
        String notPresentString = "AVOCADO";

        assertTrue(compareCaseInsensitiveWithMultipleStringsUsingStringUtils(presentString, "Mango", "Papaya", "Pineapple", "Apple"));
        assertFalse(compareCaseInsensitiveWithMultipleStringsUsingStringUtils(notPresentString, "Mango", "Papaya", "Pineapple", "Apple"));
    }

    @Test
    void givenStrings_whenCompareWithMultipleStringsUsingStream_thenSuccess() {
        String presentString = "Apple";
        String notPresentString = "Avocado";

        assertTrue(compareWithMultipleStringsUsingStream(presentString, "Mango", "Papaya", "Pineapple", "Apple"));
        assertFalse(compareWithMultipleStringsUsingStream(notPresentString, "Mango", "Papaya", "Pineapple", "Apple"));
    }

    @Test
    void givenStrings_whenCompareCaseInsensitiveWithMultipleStringsUsingStream_thenSuccess() {
        String presentString = "APPLE";
        String notPresentString = "AVOCADO";

        assertTrue(compareCaseInsensitiveWithMultipleStringsUsingStream(presentString, "Mango", "Papaya", "Pineapple", "Apple"));
        assertFalse(compareCaseInsensitiveWithMultipleStringsUsingStream(notPresentString, "Mango", "Papaya", "Pineapple", "Apple"));
    }

    @Test
    void givenStrings_whenCompareWithMultipleStringsUsingSet_thenSuccess() {
        String presentString = "Apple";
        String notPresentString = "Avocado";

        assertTrue(compareWithMultipleStringsUsingSet(presentString, "Mango", "Papaya", "Pineapple", "Apple"));
        assertFalse(compareWithMultipleStringsUsingSet(notPresentString, "Mango", "Papaya", "Pineapple", "Apple"));
    }

    @Test
    void givenStrings_whenCompareWithMultipleStringsUsingList_thenSuccess() {
        String presentString = "Apple";
        String notPresentString = "Avocado";

        assertTrue(compareWithMultipleStringsUsingList(presentString, "Mango", "Papaya", "Pineapple", "Apple"));
        assertFalse(compareWithMultipleStringsUsingList(notPresentString, "Mango", "Papaya", "Pineapple", "Apple"));
    }

    @Test
    void givenStrings_whenCompareWithMultipleStringsUsingRegularExpression_thenSuccess() {
        String presentString = "Apple";
        String notPresentString = "Avocado";

        assertTrue(compareWithMultipleStringsUsingRegularExpression(presentString, "Mango", "Papaya", "Pineapple", "Apple"));
        assertFalse(compareWithMultipleStringsUsingRegularExpression(notPresentString, "Mango", "Papaya", "Pineapple", "Apple"));
    }

    @Test
    void givenStrings_whenCompareCaseInsensitiveWithMultipleStringsUsingRegularExpression_thenSuccess() {
        String presentString = "APPLE";
        String notPresentString = "AVOCADO";

        assertTrue(compareCaseInsensitiveWithMultipleStringsUsingRegularExpression(presentString, "Mango", "Papaya", "Pineapple", "Apple"));
        assertFalse(compareCaseInsensitiveWithMultipleStringsUsingRegularExpression(notPresentString, "Mango", "Papaya", "Pineapple", "Apple"));
    }

    private boolean compareWithMultipleStringsUsingIf(String str, String... strs) {
        for (String s : strs) {
            if (str.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean compareWithMultipleStringsUsingStringUtils(String str, String... strs) {
        return StringUtils.equalsAny(str, strs);
    }

    private boolean compareCaseInsensitiveWithMultipleStringsUsingStringUtils(String str, String... strs) {
        return StringUtils.equalsAnyIgnoreCase(str, strs);
    }

    private boolean compareWithMultipleStringsUsingSet(String str, String... strs) {
        return Set.of(strs).contains(str);
    }

    private boolean compareWithMultipleStringsUsingList(String str, String... strs) {
        return List.of(strs).contains(str);
    }

    private boolean compareWithMultipleStringsUsingRegularExpression(String str, String... strs) {
        return str.matches(String.join("|", strs));
    }

    private boolean compareCaseInsensitiveWithMultipleStringsUsingRegularExpression(String str, String... strs) {
        return str.matches("(?i)" + String.join("|", strs));
    }

    private boolean compareWithMultipleStringsUsingStream(String str, String... strs) {
        return Arrays.stream(strs).anyMatch(str::equals);
    }

    private boolean compareCaseInsensitiveWithMultipleStringsUsingStream(String str, String... strs) {
        return Arrays.stream(strs).anyMatch(str::equalsIgnoreCase);
    }

    private boolean compareWithMultipleStringsUsingArrayUtils(String str, String... strs) {
        return ArrayUtils.contains(strs, str);
    }
}
