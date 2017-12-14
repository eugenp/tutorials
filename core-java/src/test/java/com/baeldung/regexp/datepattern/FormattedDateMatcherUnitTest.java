package com.baeldung.regexp.datepattern;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.regexp.datepattern.DateMatcher;

public class FormattedDateMatcherUnitTest {

    private DateMatcher matcher = new FormattedDateMatcher();

    @Test
    public void whenDateHasValidFormat_thenPatternMatches() {
        Assert.assertTrue(matcher.matches("2017-12-31"));
        Assert.assertTrue(matcher.matches("2018-01-01"));
    }

    @Test
    public void whenDateHasInvalidFormat_thenNoMatch() {
        Assert.assertFalse(matcher.matches("2018-01"));
        Assert.assertFalse(matcher.matches("2018-01-01-01"));
        Assert.assertFalse(matcher.matches("2018-01-XX"));
        Assert.assertFalse(matcher.matches(" 2018-01-01"));
        Assert.assertFalse(matcher.matches("2018-01-01 "));
    }
}
