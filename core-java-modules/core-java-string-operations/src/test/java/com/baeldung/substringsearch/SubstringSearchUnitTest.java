package com.baeldung.substringsearch;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * BAEL-2832: Different ways to check if a Substring could be found in a String.
 */
public class SubstringSearchUnitTest {

    @Test
    public void searchSubstringWithIndexOf() {
        Assert.assertEquals(9, "Bohemian Rhapsodyan".indexOf("Rhap"));

        // indexOf will return -1, because it's case sensitive
        Assert.assertEquals(-1, "Bohemian Rhapsodyan".indexOf("rhap"));

        // indexOf will return 9, because it's all lowercase
        Assert.assertEquals(9, "Bohemian Rhapsodyan".toLowerCase()
            .indexOf("rhap"));

        // it will return 6, because it's the first occurrence. Sorry Queen for being blasphemic
        Assert.assertEquals(6, "Bohemian Rhapsodyan".indexOf("an"));
    }

    @Test
    public void searchSubstringWithContains() {
        Assert.assertTrue("Hey Ho, let's go".contains("Hey"));

        // contains will return false, because it's case sensitive
        Assert.assertFalse("Hey Ho, let's go".contains("hey"));

        // contains will return true, because it's all lowercase
        Assert.assertTrue("Hey Ho, let's go".toLowerCase().contains("hey"));

        // contains will return false, because 'jey' can't be found
        Assert.assertFalse("Hey Ho, let's go".contains("jey"));
    }

    @Test
    public void searchSubstringWithStringUtils() {
        Assert.assertTrue(StringUtils.containsIgnoreCase("Runaway train", "train"));

        // it will also be true, because ignores case ;)
        Assert.assertTrue(StringUtils.containsIgnoreCase("Runaway train", "Train"));
    }

    @Test
    public void searchUsingPattern() {

        // We create the Pattern first
        Pattern pattern = Pattern.compile("(?<!\\S)" + "road" + "(?!\\S)");

        // We need to create the Matcher after
        Matcher matcher = pattern.matcher("Hit the road Jack");

        // find will return true when the first match is found
        Assert.assertTrue(matcher.find());

        // We will create a different matcher with a different text
        matcher = pattern.matcher("and don't you come back no more");

        // find will return false, because 'road' can't be find as a substring
        Assert.assertFalse(matcher.find());
    }
}