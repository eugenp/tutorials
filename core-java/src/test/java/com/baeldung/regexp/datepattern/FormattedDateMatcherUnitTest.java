package com.baeldung.regexp.datepattern;

import com.baeldung.regexp.datepattern.gregorian.testhelper.GregorianDateTestHelper;
import org.junit.Test;

public class FormattedDateMatcherUnitTest {

    private DateMatcher matcher = new FormattedDateMatcher();

    private GregorianDateTestHelper testHelper = new GregorianDateTestHelper(matcher);

    @Test
    public void whenUsingFormattedDateMatcher_thenFormatConstraintsSatisfied() {
        testHelper.assertFormat();
    }
}
