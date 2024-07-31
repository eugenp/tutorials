package com.baeldung.checkcase;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

class CaseChecker {

    static boolean allUpper1(String input) {
        return input.equals(input.toUpperCase());
    }

    static boolean allLower1(String input) {
        return input.equals(input.toLowerCase());
    }

    static boolean allUpper2(String input) {
        for (char c : input.toCharArray()) {
            //  don't write in this way: if (!Character.isUpperCase(c))
            if (Character.isLowerCase(c)) {
                return false;
            }
        }
        return true;
    }

    static boolean allLower2(String input) {
        for (char c : input.toCharArray()) {
            //  don't write in this way: if (!Character.isLowerCase(c))
            if (Character.isUpperCase(c)) {
                return false;
            }
        }
        return true;
    }

    static boolean allUpper3(String input) {
        return input.chars()
          .noneMatch(Character::isLowerCase);
    }

    static boolean allLower3(String input) {
        return input.chars()
          .noneMatch(Character::isUpperCase);
    }
}

public class StringAllUpperOrLowercaseUnitTest {
    private static final String UPPER_INPUT = "1: COOL!";
    private static final String LOWER_INPUT = "2: cool!";
    private static final String MIXED_INPUT = "3: Cool！";

    @Test
    void whenComparingToConvertedString_thenGetExpectedResult() {
        assertTrue(CaseChecker.allLower1(LOWER_INPUT));
        assertFalse(CaseChecker.allLower1(UPPER_INPUT));
        assertFalse(CaseChecker.allLower1(MIXED_INPUT));

        assertFalse(CaseChecker.allUpper1(LOWER_INPUT));
        assertTrue(CaseChecker.allUpper1(UPPER_INPUT));
        assertFalse(CaseChecker.allUpper1(MIXED_INPUT));
    }

    @Test
    void whenCheckCharInArray_thenGetExpectedResult() {
        assertTrue(CaseChecker.allLower2(LOWER_INPUT));
        assertFalse(CaseChecker.allLower2(UPPER_INPUT));
        assertFalse(CaseChecker.allLower2(MIXED_INPUT));

        assertFalse(CaseChecker.allUpper2(LOWER_INPUT));
        assertTrue(CaseChecker.allUpper2(UPPER_INPUT));
        assertFalse(CaseChecker.allUpper2(MIXED_INPUT));
    }

    @Test
    void whenUsingStream_thenGetExpectedResult() {
        assertTrue(CaseChecker.allLower3(LOWER_INPUT));
        assertFalse(CaseChecker.allLower3(UPPER_INPUT));
        assertFalse(CaseChecker.allLower3(MIXED_INPUT));

        assertFalse(CaseChecker.allUpper3(LOWER_INPUT));
        assertTrue(CaseChecker.allUpper3(UPPER_INPUT));
        assertFalse(CaseChecker.allUpper3(MIXED_INPUT));
    }

    @Test
    void whenUsingApacheCommons_thenGetExpectedResult() {
        assertFalse(StringUtils.isAllLowerCase(LOWER_INPUT));
        assertFalse(StringUtils.isAllLowerCase(UPPER_INPUT));
        assertFalse(StringUtils.isAllLowerCase(MIXED_INPUT));

        assertFalse(StringUtils.isAllLowerCase("a b"));
        assertTrue(StringUtils.isAllLowerCase("ab"));

        assertFalse(StringUtils.isAllUpperCase(LOWER_INPUT));
        assertFalse(StringUtils.isAllUpperCase(UPPER_INPUT));
        assertFalse(StringUtils.isAllUpperCase(MIXED_INPUT));

        assertFalse(StringUtils.isAllUpperCase("A B"));
        assertTrue(StringUtils.isAllUpperCase("AB"));
    }
}