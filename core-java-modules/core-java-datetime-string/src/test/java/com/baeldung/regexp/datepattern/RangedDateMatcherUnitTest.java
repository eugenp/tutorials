package com.baeldung.regexp.datepattern;

import org.junit.Assert;
import org.junit.Test;

public class RangedDateMatcherUnitTest {

    private DateMatcher matcher = new RangedDateMatcher();

    @Test
    public void whenUsingRangedDateMatcher_thenFormatConstraintsSatisfied() {
        Assert.assertFalse(matcher.matches("2018-01"));
        Assert.assertFalse(matcher.matches("2018-01-01-01"));
        Assert.assertFalse(matcher.matches("2018-01-XX"));
        Assert.assertFalse(matcher.matches(" 2018-01-01"));
        Assert.assertFalse(matcher.matches("2018-01-01 "));
        Assert.assertFalse(matcher.matches("2018/01/01"));
    }

    @Test
    public void whenUsingRangedDateMatcher_thenRangeConstraintsSatisfied() {
        Assert.assertTrue(matcher.matches("1900-01-01"));
        Assert.assertTrue(matcher.matches("2018-02-31"));
        Assert.assertTrue(matcher.matches("2999-12-31"));

        Assert.assertFalse(matcher.matches("1899-12-31"));
        Assert.assertFalse(matcher.matches("2018-05-35"));
        Assert.assertFalse(matcher.matches("2018-13-05"));
        Assert.assertFalse(matcher.matches("3000-01-01"));
    }
}
