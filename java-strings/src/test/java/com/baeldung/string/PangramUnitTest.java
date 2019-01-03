package com.baeldung.string;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


public class PangramUnitTest {
    
    @Test
    public void givenValidString_isPanagram_shouldReturnSuccess() {
        String input = "Two driven jocks help fax my big quiz";
        assertTrue(Pangram.isPangram(input));  
        assertTrue(Pangram.isPangramWithStreams(input));        
    }
    
    @Test
    public void givenNullString_isPanagram_shouldReturnFailure() {
        String input = null;
        assertFalse(Pangram.isPangram(input));
        assertFalse(Pangram.isPangramWithStreams(input));
        assertFalse(Pangram.isPerfectPangram(input));
    }
    
    @Test
    public void givenPerfectPanagramString_isPerfectPanagram_shouldReturnSuccess() {
        String input = "abcdefghijklmNoPqrStuVwxyz";
        assertTrue(Pangram.isPerfectPangram(input));         
    }
    
    @Test
    public void givenNonPanagramString_isPanagram_shouldReturnFailure() {
        String input = "invalid panagram";
        assertFalse(Pangram.isPangram(input));
        assertFalse(Pangram.isPangramWithStreams(input));
    }
    
    @Test
    public void givenPanagram_isPerfectPanagram_shouldReturnFailure() {
        String input = "Two driven jocks help fax my big quiz";
        assertFalse(Pangram.isPerfectPangram(input));
    }

}
