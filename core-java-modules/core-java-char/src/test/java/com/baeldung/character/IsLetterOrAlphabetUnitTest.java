package com.baeldung.character;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class IsLetterOrAlphabetUnitTest {
    @Test
    public void givenACharacter_whenLetter_thenAssertIsLetterTrue() {
        assertTrue(Character.isLetter('a'));
    }

    @Test
    public void givenACharacter_whenLetter_thenAssertIsAlphabeticTrue() {
        assertTrue(Character.isAlphabetic('a'));
    }

    @Test
    public void givenACharacter_whenAlphabeticAndNotLetter_thenAssertIsLetterFalse() {
        assertFalse(Character.isLetter('\u2164'));
    }

    @Test
    public void givenACharacter_whenAlphabeticAndNotLetter_thenAssertIsAlphabeticTrue() {
        assertTrue(Character.isAlphabetic('\u2164'));
    }
}
