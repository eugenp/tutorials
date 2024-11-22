package com.baeldung.regexp.datepattern.gregorian;

import org.junit.Test;

import com.baeldung.regexp.datepattern.DateMatcher;
import com.baeldung.regexp.datepattern.gregorian.testhelper.GregorianDateTestHelper;

public class FebruaryGeneralMatcherUnitTest {

    private DateMatcher matcher = new FebruaryGeneralMatcher();

    private GregorianDateTestHelper testHelper = new GregorianDateTestHelper(matcher);

    @Test
    public void whenMonthIsFebruary_thenMonthContainsUpTo28Days() {
        testHelper.assertFebruaryGeneralDates();
    }
}