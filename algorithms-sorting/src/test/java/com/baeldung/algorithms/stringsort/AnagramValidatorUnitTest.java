package com.baeldung.algorithms.stringsort;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
