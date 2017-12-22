package com.baeldung.regexp.datepattern;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.regexp.datepattern.DateMatcher;
import com.baeldung.regexp.datepattern.GregorianDateMatcher;

public class GregorianDateMatcherUnitTest {

    private DateMatcher matcher = new GregorianDateMatcher();

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

    @Test
    public void whenYearIsLeap_thenFebHas29Days() {
        Assert.assertTrue(matcher.matches("2000-02-29"));
        Assert.assertTrue(matcher.matches("2400-02-29"));
        Assert.assertTrue(matcher.matches("2800-02-29"));
        Assert.assertTrue(matcher.matches("2004-02-29"));
        Assert.assertTrue(matcher.matches("2008-02-29"));
        Assert.assertTrue(matcher.matches("2012-02-29"));
        Assert.assertTrue(matcher.matches("2016-02-29"));
        Assert.assertTrue(matcher.matches("2020-02-29"));
    }

    @Test
    public void whenYearIsNotLeap_thenFebHasNo29Days() {
        Assert.assertFalse(matcher.matches("2017-02-29"));
        Assert.assertFalse(matcher.matches("2018-02-29"));
        Assert.assertFalse(matcher.matches("2019-02-29"));
        Assert.assertFalse(matcher.matches("2100-02-29"));
        Assert.assertFalse(matcher.matches("2200-02-29"));
        Assert.assertFalse(matcher.matches("2300-02-29"));
    }

    @Test
    public void whenMonthIsFeb_thenMonthHasNo30Days() {
        Assert.assertFalse(matcher.matches("2000-02-30"));
        Assert.assertFalse(matcher.matches("2400-02-30"));
        Assert.assertFalse(matcher.matches("2420-02-30"));
        Assert.assertFalse(matcher.matches("2421-02-30"));
    }

    @Test
    public void whenMonthIsLong_thenMonthHas31Days() {
        Assert.assertTrue(matcher.matches("2018-01-31"));
        Assert.assertTrue(matcher.matches("2018-03-31"));
        Assert.assertTrue(matcher.matches("2018-05-31"));
        Assert.assertTrue(matcher.matches("2018-07-31"));
        Assert.assertTrue(matcher.matches("2018-08-31"));
        Assert.assertTrue(matcher.matches("2018-10-31"));
        Assert.assertTrue(matcher.matches("2018-12-31"));
    }

    @Test
    public void whenMonthIsShort_thenMonthHasNo31Days() {
        Assert.assertFalse(matcher.matches("2018-02-31"));
        Assert.assertFalse(matcher.matches("2018-04-31"));
        Assert.assertFalse(matcher.matches("2018-06-31"));
        Assert.assertFalse(matcher.matches("2018-09-31"));
        Assert.assertFalse(matcher.matches("2018-11-31"));
    }
}
