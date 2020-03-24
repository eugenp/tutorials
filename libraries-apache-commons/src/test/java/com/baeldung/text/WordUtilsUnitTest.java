package com.baeldung.text;

import org.apache.commons.text.WordUtils;
import org.junit.Assert;
import org.junit.Test;

public class WordUtilsUnitTest {

    @Test
    public void whenCapitalized_thenCorrect() {
        String toBeCapitalized = "to be capitalized!";
        String result = WordUtils.capitalize(toBeCapitalized);

        Assert.assertEquals("To Be Capitalized!", result);
    }

    @Test
    public void whenContainsWords_thenCorrect() {
        boolean containsWords = WordUtils.containsAllWords("String to search", "to", "search");

        Assert.assertTrue(containsWords);
    }
}
