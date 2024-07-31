package com.baeldung.regexp.datepattern.gregorian;

import com.baeldung.regexp.datepattern.DateMatcher;
import com.baeldung.regexp.datepattern.gregorian.testhelper.GregorianDateTestHelper;
import org.junit.Test;

public class FebruaryGeneralMatcherUnitTest {

    private DateMatcher matcher = new FebruaryGeneralMatcher();

    private GregorianDateTestHelper testHelper = new GregorianDateTestHelper(matcher);

    @Test
    public void whenMonthIsFebruary_thenMonthContainsUpTo28Days() {
        testHelper.assertFebruaryGeneralDates();
    }
}