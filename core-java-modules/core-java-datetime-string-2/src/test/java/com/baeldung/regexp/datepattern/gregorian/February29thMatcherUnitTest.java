package com.baeldung.regexp.datepattern.gregorian;

import org.junit.Test;

import com.baeldung.regexp.datepattern.DateMatcher;
import com.baeldung.regexp.datepattern.gregorian.testhelper.GregorianDateTestHelper;

public class February29thMatcherUnitTest {

    private DateMatcher matcher = new February29thMatcher();

    private GregorianDateTestHelper testHelper = new GregorianDateTestHelper(matcher);

    @Test
    public void whenYearIsLeap_thenYearHasFebruary29th() {
        testHelper.assertFebruary29th();
    }
}
