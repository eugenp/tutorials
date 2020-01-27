package com.baeldung.contains;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;

/**
 * BAEL-3739: Different ways to solve the contains() case insensitive behavior.
 */
public class CaseInsensitiveWorkaroundsUnitTest {

    private String src = "Lorem ipsum dolor sit amet";
    private String dest = "lorem";

    @Test
    public void givenString_whenCallingContainsWithToLowerOrUpperCase_shouldReturnTrue() {
        // Use toLowerCase to avoid case insensitive issues
        Assert.assertTrue(src.toLowerCase().contains(dest.toLowerCase()));

        // Use toUpperCase to avoid case insensitive issues
        Assert.assertTrue(src.toUpperCase().contains(dest.toUpperCase()));
    }
    
    @Test
    public void givenString_whenCallingStringMatches_shouldReturnTrue() {
        // Use String Matches to avoid case insensitive issues
        Assert.assertTrue(src.matches("(?i).*" + dest + ".*"));
    }
    
    @Test
    public void givenString_whenCallingStringRegionMatches_shouldReturnTrue() {
        // Use String Region Matches to avoid case insensitive issues
        CaseInsensitiveWorkarounds comparator = new CaseInsensitiveWorkarounds();
        Assert.assertTrue(comparator.processRegionMatches(src, dest));
    }
    
    
    @Test
    public void givenString_whenCallingPaternCompileMatcherFind_shouldReturnTrue() {
        // Use Pattern Compile Matcher and Find to avoid case insensitive issues
        Assert.assertTrue(Pattern.compile(Pattern.quote(dest), 
            Pattern.CASE_INSENSITIVE) .matcher(src) .find());
    }
    
    @Test
    public void givenString_whenCallingStringUtilsContainsIgnoreCase_shouldReturnTrue() {
        // Use StringUtils containsIgnoreCase to avoid case insensitive issues
        Assert.assertTrue(StringUtils.containsIgnoreCase(src, dest));
    }

}