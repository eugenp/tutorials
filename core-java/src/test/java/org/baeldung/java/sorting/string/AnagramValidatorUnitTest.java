package org.baeldung.java.sorting.string;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import com.baeldung.sorting.string.anagram.AnagramValidator;

class AnagramValidatorUnitTest {
    
    @Test
    void givenValidAnagrams_whenSorted_thenEqual() {
        boolean isValidAnagram = AnagramValidator.isValid("Avida Dollars", "Salvador Dali");
        
        assertTrue(isValidAnagram);
    }
    
    @Test
    void givenNotValidAnagrams_whenSorted_thenNotEqual() {
        boolean isValidAnagram = AnagramValidator.isValid("abc", "def");
        
        assertFalse(isValidAnagram);
    }
}
