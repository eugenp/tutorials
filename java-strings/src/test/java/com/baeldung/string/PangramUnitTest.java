package com.baeldung.string;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


public class PangramUnitTest {
    
    @Test
    public void givenValidString_isPanagram_shouldReturnSuccess() {
        String input = "Two driven jocks help fax my big quiz";
        assertTrue(Pangram.isPanagram(input));  
        assertTrue(Pangram.isPanagramWithStreams(input));        
    }
    
    @Test
    public void givenNullString_isPanagram_shouldReturnFailure() {
        String input = null;
        assertFalse(Pangram.isPanagram(input));
        assertFalse(Pangram.isPanagramWithStreams(input));
        assertFalse(Pangram.isPerfectPanagram(input));
    }
    
    @Test
    public void givenPerfectPanagramString_isPerfectPanagram_shouldReturnSuccess() {
        String input = "abcdefghijklmNoPqrStuVwxyz";
        assertTrue(Pangram.isPerfectPanagram(input));         
    }
    
    @Test
    public void givenNonPanagramString_isPanagram_shouldReturnFailure() {
        String input = "invalid panagram";
        assertFalse(Pangram.isPanagram(input));
        assertFalse(Pangram.isPanagramWithStreams(input));
    }
    
    @Test
    public void givenPanagram_isPerfectPanagram_shouldReturnFailure() {
        String input = "Two driven jocks help fax my big quiz";
        assertFalse(Pangram.isPerfectPanagram(input));
    }

}
