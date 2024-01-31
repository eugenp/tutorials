package com.baeldung.countupperandlowercasechars;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountUpperAndLowercaseCharsUnitTest {
    private static final String MY_STRING = "Hi, Welcome to Baeldung! Let's count letters!";
    private static final LetterCount EXPECTED = new LetterCount(4, 31);

    @Test
    void whenIteratingCharArrayCompareAndCount_thenGetExpectedResult() {
        int upperCnt = 0;
        int lowerCnt = 0;
        for (char c : MY_STRING.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                upperCnt++;
            }
            if (c >= 'a' && c <= 'z') {
                lowerCnt++;
            }
        }
        LetterCount result = new LetterCount(upperCnt, lowerCnt);
        assertEquals(EXPECTED, result);
    }

    @Test
    void whenUsingCharacterIsLowerOrUpperCase_thenGetExpectedResult() {
        int upperCnt = 0;
        int lowerCnt = 0;
        for (char c : MY_STRING.toCharArray()) {
            if (Character.isUpperCase(c)) {
                upperCnt++;
            }
            if (Character.isLowerCase(c)) {
                lowerCnt++;
            }
        }
        LetterCount result = new LetterCount(upperCnt, lowerCnt);
        assertEquals(EXPECTED, result);
    }

    @Test
    void whenUsingStreamFilterAndCount_thenGetExpectedResult() {
        LetterCount result = new LetterCount(
            (int) MY_STRING.chars().filter(Character::isUpperCase).count(),
            (int) MY_STRING.chars().filter(Character::isLowerCase).count()
        );
        assertEquals(EXPECTED, result);
    }
}

class LetterCount {
    private int uppercaseCnt;
    private int lowercaseCnt;

    public LetterCount(int uppercaseCnt, int lowercaseCnt) {
        this.uppercaseCnt = uppercaseCnt;
        this.lowercaseCnt = lowercaseCnt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LetterCount)) {
            return false;
        }

        LetterCount that = (LetterCount) o;

        if (uppercaseCnt != that.uppercaseCnt) {
            return false;
        }
        return lowercaseCnt == that.lowercaseCnt;
    }

    @Override
    public int hashCode() {
        int result = uppercaseCnt;
        result = 31 * result + lowercaseCnt;
        return result;
    }
}