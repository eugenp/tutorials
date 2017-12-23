package com.baeldung.regexp.datepattern;

import org.junit.Assert;
import org.junit.Test;

public class FormattedDateMatcherUnitTest {

    private DateMatcher matcher = new FormattedDateMatcher();

    @Test
    public void whenUsingFormattedDateMatcher_thenFormatConstraintsSatisfied() {
        Assert.assertTrue(matcher.matches("2017-12-31"));
        Assert.assertTrue(matcher.matches("2018-01-01"));
        Assert.assertTrue(matcher.matches("0000-00-00"));
        Assert.assertTrue(matcher.matches("1029-99-72"));

        Assert.assertFalse(matcher.matches("2018-01"));
        Assert.assertFalse(matcher.matches("2018-01-01-01"));
        Assert.assertFalse(matcher.matches("2018-01-XX"));
        Assert.assertFalse(matcher.matches(" 2018-01-01"));
        Assert.assertFalse(matcher.matches("2018-01-01 "));
        Assert.assertFalse(matcher.matches("2018/01/01"));
    }
}
