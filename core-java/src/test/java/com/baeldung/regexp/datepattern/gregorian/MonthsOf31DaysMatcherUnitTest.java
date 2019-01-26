package com.baeldung.regexp.datepattern.gregorian;

import com.baeldung.regexp.datepattern.DateMatcher;
import com.baeldung.regexp.datepattern.gregorian.testhelper.GregorianDateTestHelper;
import org.junit.Test;

public class MonthsOf31DaysMatcherUnitTest {

    private DateMatcher matcher = new MonthsOf31DaysMatcher();

    private GregorianDateTestHelper testHelper = new GregorianDateTestHelper(matcher);

    @Test
    public void whenMonthIsLong_thenMonthContainsUpTo31Days() {
        testHelper.assertMonthsOf31Dates();
    }
}