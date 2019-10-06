package com.baeldung.regexp.datepattern.gregorian.testhelper;

import com.baeldung.regexp.datepattern.DateMatcher;
import org.junit.Assert;

public class GregorianDateTestHelper {

    private final DateMatcher matcher;

    public GregorianDateTestHelper(DateMatcher matcher) {
        this.matcher = matcher;
    }

    public void assertFormat() {
        Assert.assertTrue(matcher.matches("2017-12-31"));
        Assert.assertTrue(matcher.matches("2018-01-01"));

        Assert.assertFalse(matcher.matches("2018-02"));
        Assert.assertFalse(matcher.matches("2018-02-01-01"));
        Assert.assertFalse(matcher.matches("2018-02-XX"));
        Assert.assertFalse(matcher.matches(" 2018-02-01"));
        Assert.assertFalse(matcher.matches("2018-02-01 "));
        Assert.assertFalse(matcher.matches("2020/02/28"));
        Assert.assertFalse(matcher.matches("2020.02.29"));
    }

    public void assertRange() {
        Assert.assertTrue(matcher.matches("1900-01-01"));
        Assert.assertTrue(matcher.matches("2205-05-25"));
        Assert.assertTrue(matcher.matches("2999-12-31"));

        Assert.assertFalse(matcher.matches("1899-12-31"));
        Assert.assertFalse(matcher.matches("2018-05-35"));
        Assert.assertFalse(matcher.matches("2018-13-05"));
        Assert.assertFalse(matcher.matches("3000-01-01"));
        Assert.assertFalse(matcher.matches("3200-02-29"));
    }

    public void assertFebruary29th() {
        Assert.assertTrue(matcher.matches("2000-02-29"));
        Assert.assertTrue(matcher.matches("2400-02-29"));
        Assert.assertTrue(matcher.matches("2800-02-29"));
        Assert.assertTrue(matcher.matches("2020-02-29"));
        Assert.assertTrue(matcher.matches("2024-02-29"));
        Assert.assertTrue(matcher.matches("2028-02-29"));

        Assert.assertFalse(matcher.matches("2017-02-29"));
        Assert.assertFalse(matcher.matches("2018-02-29"));
        Assert.assertFalse(matcher.matches("2019-02-29"));
        Assert.assertFalse(matcher.matches("2100-02-29"));
        Assert.assertFalse(matcher.matches("2200-02-29"));
        Assert.assertFalse(matcher.matches("2300-02-29"));
    }

    public void assertFebruaryGeneralDates() {
        Assert.assertTrue(matcher.matches("2018-02-01"));
        Assert.assertTrue(matcher.matches("2019-02-13"));
        Assert.assertTrue(matcher.matches("2020-02-25"));

        Assert.assertFalse(matcher.matches("2000-02-30"));
        Assert.assertFalse(matcher.matches("2400-02-62"));
        Assert.assertFalse(matcher.matches("2420-02-94"));
    }

    public void assertMonthsOf30Days() {
        Assert.assertTrue(matcher.matches("2018-04-30"));
        Assert.assertTrue(matcher.matches("2019-06-30"));
        Assert.assertTrue(matcher.matches("2020-09-30"));
        Assert.assertTrue(matcher.matches("2021-11-30"));

        Assert.assertTrue(matcher.matches("2022-04-02"));
        Assert.assertTrue(matcher.matches("2023-06-14"));
        Assert.assertTrue(matcher.matches("2024-09-26"));

        Assert.assertFalse(matcher.matches("2018-04-31"));
        Assert.assertFalse(matcher.matches("2019-06-31"));
        Assert.assertFalse(matcher.matches("2020-09-31"));
        Assert.assertFalse(matcher.matches("2021-11-31"));

        Assert.assertFalse(matcher.matches("2022-04-32"));
        Assert.assertFalse(matcher.matches("2023-06-64"));
        Assert.assertFalse(matcher.matches("2024-09-96"));
    }

    public void assertMonthsOf31Dates() {
        Assert.assertTrue(matcher.matches("2018-01-31"));
        Assert.assertTrue(matcher.matches("2019-03-31"));
        Assert.assertTrue(matcher.matches("2020-05-31"));
        Assert.assertTrue(matcher.matches("2021-07-31"));
        Assert.assertTrue(matcher.matches("2022-08-31"));
        Assert.assertTrue(matcher.matches("2023-10-31"));
        Assert.assertTrue(matcher.matches("2024-12-31"));

        Assert.assertTrue(matcher.matches("2025-01-03"));
        Assert.assertTrue(matcher.matches("2026-03-15"));
        Assert.assertTrue(matcher.matches("2027-05-27"));

        Assert.assertFalse(matcher.matches("2018-01-32"));
        Assert.assertFalse(matcher.matches("2019-03-64"));
        Assert.assertFalse(matcher.matches("2020-05-96"));
    }
}
