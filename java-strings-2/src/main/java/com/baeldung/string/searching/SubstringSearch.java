package com.baeldung.string.searching;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * BAEL-2832: Different ways to check if a Substring could be found in a String.
 */
public class SubstringSearch {

    @Test
    public void searchSubstringWithIndexOf() {
        Assert.assertEquals("Bohemian Rhapsodyan".indexOf("Rhap"), 9);

        // indexOf will return -1, because it's case sensitive
        Assert.assertEquals("Bohemian Rhapsodyan".indexOf("rhap"), -1);

        // indexOf will return 9, because it's all lowercase
        Assert.assertEquals("Bohemian Rhapsodyan".toLowerCase()
            .indexOf("rhap"), 9);

        // it will return 6, because it's the first occurrence. Sorry Queen for being blasphemic
        Assert.assertEquals("Bohemian Rhapsodyan".indexOf("an"), 6);
    }

    @Test
    public void searchSubstringWithContains() {
        Assert.assertTrue("Hey Ho, let's go".contains("Hey"));

        // contains will return false, because it's case sensitive
        Assert.assertFalse("Hey Ho, let's go".contains("hey"));

        // contains will return true, because it's all lowercase
        Assert.assertTrue("Hey Ho, let's go".toLowerCase()
            .contains("hey"));

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