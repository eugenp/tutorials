/**
 * 
 */
package com.baeldung.string;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author swpraman
 *
 */
public class AppendCharAtPositionXUnitTest {
    
    private AppendCharAtPositionX appendCharAtPosition = new AppendCharAtPositionX();
    private String word = "Titanc";
    private char letter = 'i';
    
    @Test
    public void whenUsingCharacterArrayAndCharacterAddedAtBeginning_shouldAddCharacter() {
        assertEquals("iTitanc", appendCharAtPosition.addCharUsingCharArray(word, letter, 0));
    }
    
    @Test
    public void whenUsingSubstringAndCharacterAddedAtBeginning_shouldAddCharacter() {
        assertEquals("iTitanc", appendCharAtPosition.addCharUsingSubstring(word, letter, 0));
    }  
    
    @Test
    public void whenUsingStringBuilderAndCharacterAddedAtBeginning_shouldAddCharacter() {
        assertEquals("iTitanc", appendCharAtPosition.addCharUsingStringBuilder(word, letter, 0));
    }
    
    @Test
    public void whenUsingCharacterArrayAndCharacterAddedAtMiddle_shouldAddCharacter() {
        assertEquals("Titianc", appendCharAtPosition.addCharUsingCharArray(word, letter, 3));
    }
    
    @Test
    public void whenUsingSubstringAndCharacterAddedAtMiddle_shouldAddCharacter() {
        assertEquals("Titianc", appendCharAtPosition.addCharUsingSubstring(word, letter, 3));
    }  
    
    @Test
    public void whenUsingStringBuilderAndCharacterAddedAtMiddle_shouldAddCharacter() {
        assertEquals("Titianc", appendCharAtPosition.addCharUsingStringBuilder(word, letter, 3));
    }
    
    @Test
    public void whenUsingCharacterArrayAndCharacterAddedAtEnd_shouldAddCharacter() {
        assertEquals("Titanci", appendCharAtPosition.addCharUsingCharArray(word, letter, word.length()));
    }
    
    @Test
    public void whenUsingSubstringAndCharacterAddedAtEnd_shouldAddCharacter() {
        assertEquals("Titanci", appendCharAtPosition.addCharUsingSubstring(word, letter, word.length()));
    }  
    
    @Test
    public void whenUsingStringBuilderAndCharacterAddedAtEnd_shouldAddCharacter() {
        assertEquals("Titanci", appendCharAtPosition.addCharUsingStringBuilder(word, letter, word.length()));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void whenUsingCharacterArrayAndCharacterAddedAtNegativePosition_shouldThrowException() {
        appendCharAtPosition.addCharUsingStringBuilder(word, letter, -1);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void whenUsingSubstringAndCharacterAddedAtNegativePosition_shouldThrowException() {
        appendCharAtPosition.addCharUsingStringBuilder(word, letter, -1);
    }  
    
    @Test(expected=IllegalArgumentException.class)
    public void whenUsingStringBuilderAndCharacterAddedAtNegativePosition_shouldThrowException() {
        appendCharAtPosition.addCharUsingStringBuilder(word, letter, -1);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void whenUsingCharacterArrayAndCharacterAddedAtInvalidPosition_shouldThrowException() {
        appendCharAtPosition.addCharUsingStringBuilder(word, letter, word.length() + 2);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void whenUsingSubstringAndCharacterAddedAtInvalidPosition_shouldThrowException() {
        appendCharAtPosition.addCharUsingStringBuilder(word, letter, word.length() + 2);
    }  
    
    @Test(expected=IllegalArgumentException.class)
    public void whenUsingStringBuilderAndCharacterAddedAtInvalidPosition_shouldThrowException() {
        appendCharAtPosition.addCharUsingStringBuilder(word, letter, word.length() + 2);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void whenUsingCharacterArrayAndCharacterAddedAtPositionXAndStringIsNull_shouldThrowException() {
        appendCharAtPosition.addCharUsingStringBuilder(null, letter, 3);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void whenUsingSubstringAndCharacterAddedAtPositionXAndStringIsNull_shouldThrowException() {
        appendCharAtPosition.addCharUsingStringBuilder(null, letter, 3);
    }  
    
    @Test(expected=IllegalArgumentException.class)
    public void whenUsingStringBuilderAndCharacterAddedAtPositionXAndStringIsNull_shouldThrowException() {
        appendCharAtPosition.addCharUsingStringBuilder(null, letter, 3);
    }
 
}
