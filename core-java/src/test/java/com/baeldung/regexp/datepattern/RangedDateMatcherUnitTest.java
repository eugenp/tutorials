package com.baeldung.regexp.datepattern;

import com.baeldung.regexp.datepattern.gregorian.testhelper.GregorianDateTestHelper;
import org.junit.Test;

public class RangedDateMatcherUnitTest {

    private DateMatcher matcher = new RangedDateMatcher();

    private GregorianDateTestHelper testHelper = new GregorianDateTestHelper(matcher);

    @Test
    public void whenUsingRangedDateMatcher_thenFormatConstraintsSatisfied() {
        testHelper.assertFormat();
    }

    @Test
    public void whenUsingRangedDateMatcher_thenRangeConstraintsSatisfied() {
        testHelper.assertRange();
    }
}
