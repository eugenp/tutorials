package com.baeldung.regexp.datepattern;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.regexp.datepattern.DateMatcher;

public class FormattedDateMatcherUnitTest {

    private DateMatcher matcher = new FormattedDateMatcher();

    @Test
    public void whenDateHasValidFormat_thenPatternMatches() {
        Assert.assertTrue(matcher.match("2017-12-31"));
        Assert.assertTrue(matcher.match("2018-01-01"));
    }

    @Test
    public void whenDateHasInvalidFormat_thenNoMatch() {
        Assert.assertFalse(matcher.match("2018-01"));
        Assert.assertFalse(matcher.match("2018-01-01-01"));
        Assert.assertFalse(matcher.match("2018-01-XX"));
        Assert.assertFalse(matcher.match(" 2018-01-01"));
        Assert.assertFalse(matcher.match("2018-01-01 "));
    }
}
