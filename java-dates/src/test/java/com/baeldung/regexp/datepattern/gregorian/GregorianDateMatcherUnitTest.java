package com.baeldung.regexp.datepattern.gregorian;

import com.baeldung.regexp.datepattern.DateMatcher;
import com.baeldung.regexp.datepattern.gregorian.testhelper.GregorianDateTestHelper;
import org.junit.Test;

public class GregorianDateMatcherUnitTest {

    private DateMatcher matcher = new GregorianDateMatcher();

    private GregorianDateTestHelper testHelper = new GregorianDateTestHelper(matcher);

    @Test
    public void whenUsingGregorianDateMatcher_thenFormatConstraintsSatisfied() {
        testHelper.assertFormat();
    }

    @Test
    public void whenUsingGregorianDateMatcher_thenRangeConstraintsSatisfied() {
        testHelper.assertRange();
    }

    @Test
    public void whenYearIsLeap_thenFebruaryHas29Days() {
        testHelper.assertFebruary29th();
    }

    @Test
    public void whenMonthIsFebruary_thenMonthContainsUpTo28Days() {
        testHelper.assertFebruaryGeneralDates();
    }

    @Test
    public void whenMonthIsShort_thenMonthContainsUpTo30Days() {
        testHelper.assertMonthsOf30Days();
    }

    @Test
    public void whenMonthIsLong_thenMonthContainsUpTo31Days() {
        testHelper.assertMonthsOf31Dates();
    }
}
