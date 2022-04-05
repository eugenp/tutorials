package com.baeldung.java14.character;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class IsLetterOrAlphabetUnitTest {

    @Test
    public void givenACharacter_whenLetter_thenAssertIsLetterTrue() {
        assertTrue(Character.isLetter(65));
    }

    @Test
    public void givenACharacter_whenLetter_thenAssertIsAlphabeticTrue() {
        assertTrue(Character.isAlphabetic(65));
    }

    @Test
    public void givenACharacter_whenAlphabeticAndNotLetter_thenAssertIsLetterFalse() {
        assertFalse(Character.isLetter(837));
    }

    @Test
    public void givenACharacter_whenAlphabeticAndNotLetter_thenAssertIsAlphabeticTrue() {
        assertTrue(Character.isAlphabetic(837));
    }

}