package com.baeldung.regexp.datepattern.validator;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.regexp.datepattern.validator.DateValidator;
import com.baeldung.regexp.datepattern.validator.PreciseDateValidator;

public class DateValidatorUnitTest {

    private DateValidator validator = new PreciseDateValidator();

    @Test
    public void whenDateHasInvalidTokens_thenValidationFails() {
        Assert.assertFalse(validator.validate("2018-01"));
        Assert.assertFalse(validator.validate("2018-01-01-01"));
        Assert.assertFalse(validator.validate("2018-01-XX"));
        Assert.assertFalse(validator.validate(" 2018-01-01"));
        Assert.assertFalse(validator.validate("2018-01-01 "));
    }

    @Test
    public void whenDateIsInBoundsOfAllowedScope_thenValidationPasses() {
        Assert.assertTrue(validator.validate("1900-01-01"));
        Assert.assertTrue(validator.validate("2017-12-31"));
        Assert.assertTrue(validator.validate("2999-12-31"));
    }

    @Test
    public void whenDateIsOutOfBoundsOfAllowedScope_thenValidationFails() {
        Assert.assertFalse(validator.validate("1899-12-31"));
        Assert.assertFalse(validator.validate("3000-01-01"));
    }

    @Test
    public void whenMonthIsJanMarMayJulAugOctDec_thenMonthHas31Days() {
        Assert.assertTrue(validator.validate("2017-01-31"));
        Assert.assertTrue(validator.validate("2017-03-31"));
        Assert.assertTrue(validator.validate("2017-05-31"));
        Assert.assertTrue(validator.validate("2017-07-31"));
        Assert.assertTrue(validator.validate("2017-08-31"));
        Assert.assertTrue(validator.validate("2017-10-31"));
        Assert.assertTrue(validator.validate("2017-12-31"));
    }

    @Test
    public void whenMonthIsFebAprJunSepNov_thenMonthHasNo31Days() {
        Assert.assertFalse(validator.validate("2017-02-31"));
        Assert.assertFalse(validator.validate("2017-04-31"));
        Assert.assertFalse(validator.validate("2017-06-31"));
        Assert.assertFalse(validator.validate("2017-09-31"));
        Assert.assertFalse(validator.validate("2017-11-31"));
    }

    @Test
    public void whenMonthIsFeb_thenMonthHasNo30Days() {
        Assert.assertFalse(validator.validate("2000-02-30"));
        Assert.assertFalse(validator.validate("2400-02-30"));
        Assert.assertFalse(validator.validate("2420-02-30"));
        Assert.assertFalse(validator.validate("2421-02-30"));
    }

    @Test
    public void whenYearIsLeap_thenFebHas29Days() {
        Assert.assertTrue(validator.validate("2000-02-29"));
        Assert.assertTrue(validator.validate("2004-02-29"));
        Assert.assertTrue(validator.validate("2008-02-29"));
        Assert.assertTrue(validator.validate("2012-02-29"));
        Assert.assertTrue(validator.validate("2016-02-29"));
        Assert.assertTrue(validator.validate("2020-02-29"));
    }

    @Test
    public void whenYearIsNotLeap_thenFebHasNo29Days() {
        Assert.assertFalse(validator.validate("2017-02-29"));
        Assert.assertFalse(validator.validate("2018-02-29"));
        Assert.assertFalse(validator.validate("2019-02-29"));
        Assert.assertFalse(validator.validate("2100-02-29"));
    }
}
