package com.baeldung.regexp.datepattern.gregorian;

import org.junit.Test;

import com.baeldung.regexp.datepattern.DateMatcher;
import com.baeldung.regexp.datepattern.gregorian.testhelper.GregorianDateTestHelper;

public class MonthsOf30DaysMatcherUnitTest {

    private DateMatcher matcher = new MonthsOf30DaysMatcher();

    private GregorianDateTestHelper testHelper = new GregorianDateTestHelper(matcher);

    @Test
    public void whenMonthIsShort_thenMonthContainsUpTo30Days() {
        testHelper.assertMonthsOf30Days();
    }
}