package com.baeldung.character;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class CharacterGeneralCategoryTypeUnitTest {
    @Test
    public void givenACharacter_whenUpperCaseLetter_thenAssertTrue() {
        assertEquals(Character.UPPERCASE_LETTER, Character.getType('U'));
    }

    @Test
    public void givenACharacter_whenLowerCaseLetter_thenAssertTrue() {
        assertEquals(Character.LOWERCASE_LETTER, Character.getType('u'));
    }

    @Test
    public void givenACharacter_whenTitleCaseLetter_thenAssertTrue() {
        assertEquals(Character.TITLECASE_LETTER, Character.getType('\u01f2'));
    }

    @Test
    public void givenACharacter_whenModifierLetter_thenAssertTrue() {
        assertEquals(Character.MODIFIER_LETTER, Character.getType('\u02b0'));
    }

    @Test
    public void givenACharacter_whenOtherLetter_thenAssertTrue() {
        assertEquals(Character.OTHER_LETTER, Character.getType('\u05d0'));
    }

    @Test
    public void givenACharacter_whenLetterNumber_thenAssertTrue() {
        assertEquals(Character.LETTER_NUMBER, Character.getType('\u2164'));
    }
}
