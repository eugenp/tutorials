package com.baeldung.substring;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class SubstringUnitTest {

    String text = "Julia Evans was born on 25-09-1984. She is currently living in the USA (United States of America).";

    @Test
    public void givenAString_whenUsedStringUtils_ShouldReturnProperSubstring() {
        Assert.assertEquals("United States of America", StringUtils.substringBetween(text, "(", ")"));
        Assert.assertEquals("the USA (United States of America).", StringUtils.substringAfter(text, "living in "));
        Assert.assertEquals("Julia Evans", StringUtils.substringBefore(text, " was born"));
    }

    @Test
    public void givenAString_whenUsedScanner_ShouldReturnProperSubstring() {
        try (Scanner scanner = new Scanner(text)) {
            scanner.useDelimiter("\\.");
            Assert.assertEquals("Julia Evans was born on 25-09-1984", scanner.next());
        }
    }

    @Test
    public void givenAString_whenUsedSplit_ShouldReturnProperSubstring() {
        String[] sentences = text.split("\\.");
        Assert.assertEquals("Julia Evans was born on 25-09-1984", sentences[0]);
    }

    @Test
    public void givenAString_whenUsedRegex_ShouldReturnProperSubstring() {
        Pattern pattern = Pattern.compile("\\d{2}\\-\\d{2}-\\d{4}");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            Assert.assertEquals("25-09-1984", matcher.group());
        }
    }

    @Test
    public void givenAString_whenUsedSubSequence_ShouldReturnProperSubstring() {
        Assert.assertEquals("USA (United States of America)", text.subSequence(67, text.length() - 1));
    }

    @Test
    public void givenAString_whenUsedSubstring_ShouldReturnProperSubstring() {
        Assert.assertEquals("USA (United States of America).", text.substring(67));
        Assert.assertEquals("USA (United States of America)", text.substring(67, text.length() - 1));
    }

    @Test
    public void givenAString_whenUsedSubstringWithIndexOf_ShouldReturnProperSubstring() {
        Assert.assertEquals("United States of America", text.substring(text.indexOf('(') + 1, text.indexOf(')')));
    }

    @Test
    public void givenAString_whenUsedSubstringWithLastIndexOf_ShouldReturnProperSubstring() {
        Assert.assertEquals("1984", text.substring(text.lastIndexOf('-') + 1, text.indexOf('.')));
    }

    @Test
    public void givenAString_whenUsedSubstringWithIndexOfAString_ShouldReturnProperSubstring() {
        Assert.assertEquals("USA (United States of America)", text.substring(text.indexOf("USA"), text.indexOf(')') + 1));
    }

}
