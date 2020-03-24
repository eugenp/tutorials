package com.baeldung.system;

import org.junit.Assert;
import org.junit.Test;

public class DateTimeServiceUnitTest {

    @Test
    public void givenClass_whenCalledMethods_thenNotNullInResult() {
        DateTimeService dateTimeService = new DateTimeService();

        Assert.assertNotNull(dateTimeService.nowPlusOneHour());
        Assert.assertNotNull(dateTimeService.nowPrettyPrinted());
    }
}
