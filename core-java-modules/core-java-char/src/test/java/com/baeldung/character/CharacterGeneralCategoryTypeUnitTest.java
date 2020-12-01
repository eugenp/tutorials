package com.baeldung.character;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CharacterGeneralCategoryTypeUnitTest {
    @Test
    public void givenACharacter_whenUpperCaseLetter_thenAssertTrue() {
        assertTrue(Character.getType('U') == Character.UPPERCASE_LETTER);
    }

    @Test
    public void givenACharacter_whenLowerCaseLetter_thenAssertTrue() {
        assertTrue(Character.getType('u') == Character.LOWERCASE_LETTER);
    }

    @Test
    public void givenACharacter_whenTitleCaseLetter_thenAssertTrue() {
        assertTrue(Character.getType('\u01f2') == Character.TITLECASE_LETTER);
    }

    @Test
    public void givenACharacter_whenModifierLetter_thenAssertTrue() {
        assertTrue(Character.getType('\u02b0') == Character.MODIFIER_LETTER);
    }

    @Test
    public void givenACharacter_whenOtherLetter_thenAssertTrue() {
        assertTrue(Character.getType('\u05d0') == Character.OTHER_LETTER);
    }

    @Test
    public void givenACharacter_whenLetterNumber_thenAssertTrue() {
        assertTrue(Character.getType('\u2164') == Character.LETTER_NUMBER);
    }
}
