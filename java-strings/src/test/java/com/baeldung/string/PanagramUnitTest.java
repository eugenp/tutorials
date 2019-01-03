package com.baeldung.string;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;


public class PanagramUnitTest {
    
    @Test
    public void givenValidString_isPanagram_shouldReturnSuccess() {
        String input = "Two driven jocks help fax my big quiz";
        assertTrue(Panagram.isPanagram(input));  
        assertTrue(Panagram.isPanagramWithStreams(input));        
    }
    
    @Test
    public void givenNullString_isPanagram_shouldReturnFailure() {
        String input = null;
        assertFalse(Panagram.isPanagram(input));
        assertFalse(Panagram.isPanagramWithStreams(input));
        assertFalse(Panagram.isPerfectPanagram(input));
    }
    
    @Test
    public void givenPerfectPanagramString_isPerfectPanagram_shouldReturnSuccess() {
        String input = "abcdefghijklmNoPqrStuVwxyz";
        assertTrue(Panagram.isPerfectPanagram(input));         
    }
    
    @Test
    public void givenNonPanagramString_isPanagram_shouldReturnFailure() {
        String input = "invalid panagram";
        assertFalse(Panagram.isPanagram(input));
        assertFalse(Panagram.isPanagramWithStreams(input));
    }
    
    @Test
    public void givenPanagram_isPerfectPanagram_shouldReturnFailure() {
        String input = "Two driven jocks help fax my big quiz";
        assertFalse(Panagram.isPerfectPanagram(input));
    }

}
