package com.baeldung.string.sorting;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import com.baeldung.string.sorting.AnagramValidator;

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
