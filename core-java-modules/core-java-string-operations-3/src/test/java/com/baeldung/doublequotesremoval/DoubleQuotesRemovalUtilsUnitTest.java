package com.baeldung.doublequotesremoval;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class DoubleQuotesRemovalUtilsUnitTest {
    
    @Test
    public void given_EmptyString_ShouldReturn_EmptyString() {
        String input = "";
        
        assertTrue(DoubleQuotesRemovalUtils.removeWithSubString(input).isEmpty());
        assertTrue(DoubleQuotesRemovalUtils.removeWithReplaceAllSimple(input).isEmpty());
        assertTrue(DoubleQuotesRemovalUtils.removeWithReplaceAllAdvanced(input).isEmpty());
        assertTrue(DoubleQuotesRemovalUtils.removeWithGuava(input).isEmpty());
    }
    
    @Test
    public void given_DoubleQuotesOnly_ShouldReturn_EmptyString() {
        String input = "\"\"";
        
        assertTrue(DoubleQuotesRemovalUtils.removeWithSubString(input).isEmpty());
        assertTrue(DoubleQuotesRemovalUtils.removeWithReplaceAllSimple(input).isEmpty());
        assertTrue(DoubleQuotesRemovalUtils.removeWithReplaceAllAdvanced(input).isEmpty());
        assertTrue(DoubleQuotesRemovalUtils.removeWithGuava(input).isEmpty());
    }
    
    @Test
    public void given_TextWithDoubleQuotes_ShouldReturn_TextOnly() {
        
        String input = "\"Example of text for this test suit\"";
        String expectedResult = "Example of text for this test suit";
        
        assertEquals(expectedResult, DoubleQuotesRemovalUtils.removeWithSubString(input));
        assertEquals(expectedResult, DoubleQuotesRemovalUtils.removeWithReplaceAllSimple(input));
        assertEquals(expectedResult, DoubleQuotesRemovalUtils.removeWithReplaceAllAdvanced(input));
        assertEquals(expectedResult, DoubleQuotesRemovalUtils.removeWithGuava(input));
    }

}
