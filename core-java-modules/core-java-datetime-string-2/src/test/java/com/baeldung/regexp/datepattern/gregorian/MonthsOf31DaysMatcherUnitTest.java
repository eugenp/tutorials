package com.baeldung.regexp.datepattern.gregorian;

import org.junit.Test;

import com.baeldung.regexp.datepattern.DateMatcher;
import com.baeldung.regexp.datepattern.gregorian.testhelper.GregorianDateTestHelper;

public class MonthsOf31DaysMatcherUnitTest {

    private DateMatcher matcher = new MonthsOf31DaysMatcher();

    private GregorianDateTestHelper testHelper = new GregorianDateTestHelper(matcher);

    @Test
    public void whenMonthIsLong_thenMonthContainsUpTo31Days() {
        testHelper.assertMonthsOf31Dates();
    }
}