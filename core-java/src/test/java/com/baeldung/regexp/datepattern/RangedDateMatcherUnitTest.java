package com.baeldung.regexp.datepattern;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.regexp.datepattern.DateMatcher;

public class RangedDateMatcherUnitTest {

    private DateMatcher matcher = new RangedDateMatcher();

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

    @Test
    public void whenDateIsInRestrictedRange_thenPatternMatches() {
        Assert.assertTrue(matcher.matches("1900-01-01"));
        Assert.assertTrue(matcher.matches("2205-05-25"));
        Assert.assertTrue(matcher.matches("2999-12-31"));
    }

    @Test
    public void whenDateIsOutOfRestrictedRange_thenNoMatch() {
        Assert.assertFalse(matcher.matches("1899-12-31"));
        Assert.assertFalse(matcher.matches("3000-01-01"));
    }
}
