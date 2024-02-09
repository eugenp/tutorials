package com.baeldung.countupperandlowercasechars;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CountUpperAndLowercaseCharsUnitTest {
    private static final String MY_STRING = "Hi, Welcome to Baeldung! Let's count letters!";

    @Test
    void whenUsingCountByCharacterRange_thenGetExpectedResult() {
        LetterCount result = LetterCount.countByCharacterRange(MY_STRING);
        assertEquals(4, result.getUppercaseCount());
        assertEquals(31, result.getLowercaseCount());
    }

    @Test
    void whenUsingCountByCharacterIsLowerOrUpperCase_thenGetExpectedResult() {
        LetterCount result = LetterCount.countByCharacterIsUpperLower(MY_STRING);
        assertEquals(4, result.getUppercaseCount());
        assertEquals(31, result.getLowercaseCount());
    }

    @Test
    void whenUsingCountByStreamApi_thenGetExpectedResult() {
        LetterCount result = LetterCount.countByStreamAPI(MY_STRING);
        assertEquals(4, result.getUppercaseCount());
        assertEquals(31, result.getLowercaseCount());
    }

    @Test
    void whenUsingIsUpperCaseAndIsLowerCase_thenUnicodeCharactersCanBeChecked() {
        assertTrue(Character.isLowerCase('ä'));
        assertTrue(Character.isUpperCase('Ä'));
    }
}

class LetterCount {
    private int uppercaseCount;
    private int lowercaseCount;

    private LetterCount(int uppercaseCount, int lowercaseCount) {
        this.uppercaseCount = uppercaseCount;
        this.lowercaseCount = lowercaseCount;
    }

    public static LetterCount countByCharacterRange(String input) {
        int upperCount = 0;
        int lowerCount = 0;
        for (char c : input.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                upperCount++;
            }
            if (c >= 'a' && c <= 'z') {
                lowerCount++;
            }
        }
        return new LetterCount(upperCount, lowerCount);
    }

    public static LetterCount countByCharacterIsUpperLower(String input) {
        int upperCount = 0;
        int lowerCount = 0;
        for (char c : input.toCharArray()) {
            if (Character.isUpperCase(c)) {
                upperCount++;
            }
            if (Character.isLowerCase(c)) {
                lowerCount++;
            }
        }
        return new LetterCount(upperCount, lowerCount);
    }

    public static LetterCount countByStreamAPI(String input) {
        return new LetterCount(
            (int) input.chars().filter(Character::isUpperCase).count(),
            (int) input.chars().filter(Character::isLowerCase).count()
        );
    }

    public int getUppercaseCount() {
        return uppercaseCount;
    }

    public int getLowercaseCount() {
        return lowercaseCount;
    }
}