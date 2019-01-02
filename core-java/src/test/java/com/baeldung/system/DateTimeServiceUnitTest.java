package com.baeldung.system;

import org.junit.Assert;
import org.junit.Test;

/**
 * 测试：DateTime
 * 说明：
 * （1）System.currentTimeMillis() + 3600 * 1000L
 * （2）new Date(System.currentTimeMillis()).toString()
 */
public class DateTimeServiceUnitTest {

    @Test
    public void givenClass_whenCalledMethods_thenNotNullInResult() {
        DateTimeService dateTimeService = new DateTimeService();
        System.out.println("dateTimeService.nowPlusOneHour():{}" + dateTimeService.nowPlusOneHour());
        System.out.println("dateTimeService.nowPrettyPrinted():{}" + dateTimeService.nowPrettyPrinted());

        Assert.assertNotNull(dateTimeService.nowPlusOneHour());
        Assert.assertNotNull(dateTimeService.nowPrettyPrinted());
    }
}
