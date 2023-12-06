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
    void givenStrings_whenCompareUsingIf_thenSuccess() {
        String presentString = "Apple";
        String notPresentString = "Avocado";

        assertTrue(compareWithAnyWithIf(presentString, "Mango", "Papaya", "Pineapple", "Apple"));
        assertFalse(compareWithAnyWithIf(notPresentString, "Mango", "Papaya", "Pineapple", "Apple"));
    }

    @Test
    void givenStrings_whenCompareUsingArrayUtils_thenSuccess() {
        String presentString = "Apple";
        String notPresentString = "Avocado";

        assertTrue(compareWithAnyUsingArrayUtils(presentString, "Mango", "Papaya", "Pineapple", "Apple"));
        assertFalse(compareWithAnyUsingArrayUtils(notPresentString, "Mango", "Papaya", "Pineapple", "Apple"));
    }

    @Test
    void givenStrings_whenCompareUsingStringUtils_thenSuccess() {
        String presentString = "Apple";
        String notPresentString = "Avocado";

        assertTrue(compareWithAnyUsingStringUtils(presentString, "Mango", "Papaya", "Pineapple", "Apple"));
        assertFalse(compareWithAnyUsingStringUtils(notPresentString, "Mango", "Papaya", "Pineapple", "Apple"));
    }

    @Test
    void givenStrings_whenCompareCaseInsensitiveUsingStringUtils_thenSuccess() {
        String presentString = "APPLE";
        String notPresentString = "AVOCADO";

        assertTrue(compareWithAnyCaseInsensitiveUsingStringUtils(presentString, "Mango", "Papaya", "Pineapple", "Apple"));
        assertFalse(compareWithAnyCaseInsensitiveUsingStringUtils(notPresentString, "Mango", "Papaya", "Pineapple", "Apple"));
    }
    @Test
    void givenStrings_whenCompareUsingStream_thenSuccess() {
        String presentString = "Apple";
        String notPresentString = "Avocado";

        assertTrue(compareWithAnyUsingStream(presentString, "Mango", "Papaya", "Pineapple", "Apple"));
        assertFalse(compareWithAnyUsingStream(notPresentString, "Mango", "Papaya", "Pineapple", "Apple"));
    }

    @Test
    void givenStrings_whenCompareCaseInsensitiveUsingStream_thenSuccess() {
        String presentString = "APPLE";
        String notPresentString = "AVOCADO";

        assertTrue(compareWithAnyCaseInsensitiveUsingStream(presentString, "Mango", "Papaya", "Pineapple", "Apple"));
        assertFalse(compareWithAnyCaseInsensitiveUsingStream(notPresentString, "Mango", "Papaya", "Pineapple", "Apple"));
    }

    @Test
    void givenStrings_whenCompareUsingSet_thenSuccess() {
        String presentString = "Apple";
        String notPresentString = "Avocado";

        assertTrue(compareWithAnyUsingSet(presentString, "Mango", "Papaya", "Pineapple", "Apple"));
        assertFalse(compareWithAnyUsingSet(notPresentString, "Mango", "Papaya", "Pineapple", "Apple"));
    }

    @Test
    void givenStrings_whenCompareUsingList_thenSuccess() {
        String presentString = "Apple";
        String notPresentString = "Avocado";

        assertTrue(compareWithAnyUsingList(presentString, "Mango", "Papaya", "Pineapple", "Apple"));
        assertFalse(compareWithAnyUsingList(notPresentString, "Mango", "Papaya", "Pineapple", "Apple"));
    }

    @Test
    void givenStrings_whenCompareUsingRegularExpression_thenSuccess() {
        String presentString = "Apple";
        String notPresentString = "Avocado";

        assertTrue(compareWithAnyUsingRegularExpression(presentString, "Mango", "Papaya", "Pineapple", "Apple"));
        assertFalse(compareWithAnyUsingRegularExpression(notPresentString, "Mango", "Papaya", "Pineapple", "Apple"));
    }

    @Test
    void givenStrings_whenCompareCaseInsensitiveUsingRegularExpression_thenSuccess() {
        String presentString = "APPLE";
        String notPresentString = "AVOCADO";

        assertTrue(compareWithAnyCaseInsensitiveUsingRegularExpression(presentString, "Mango", "Papaya", "Pineapple", "Apple"));
        assertFalse(compareWithAnyCaseInsensitiveUsingRegularExpression(notPresentString, "Mango", "Papaya", "Pineapple", "Apple"));
    }

    private boolean compareWithAnyWithIf(String str, String ... strs) {
        for(String s : strs) {
            if (str.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean compareWithAnyUsingStringUtils(String str, String ... strs) {
        return StringUtils.equalsAny(str, strs);
    }

    private boolean compareWithAnyCaseInsensitiveUsingStringUtils(String str, String ... strs) {
        return StringUtils.equalsAnyIgnoreCase(str, strs);
    }

    private boolean compareWithAnyUsingSet(String str, String ... strs) {
        return Set.of(strs).contains(str);
    }

    private boolean compareWithAnyUsingList(String str, String ... strs) {
        return List.of(strs).contains(str);
    }

    private boolean compareWithAnyUsingRegularExpression(String str, String ... strs) {
        return str.matches(String.join("|", strs));
    }

    private boolean compareWithAnyCaseInsensitiveUsingRegularExpression(String str, String ... strs) {
        return str.matches("(?i)" + String.join("|", strs));
    }

    private boolean compareWithAnyUsingStream(String str, String ... strs) {
        return Arrays.stream(strs).anyMatch(str::equals);
    }
    private boolean compareWithAnyCaseInsensitiveUsingStream(String str, String ... strs) {
        return Arrays.stream(strs).anyMatch(str::equalsIgnoreCase);
    }
    private boolean compareWithAnyUsingArrayUtils(String str, String ... strs) {
        return ArrayUtils.contains(strs, str);
    }
}
