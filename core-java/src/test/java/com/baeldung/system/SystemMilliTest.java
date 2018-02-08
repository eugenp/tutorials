package com.baeldung.system;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class SystemMilliTest {

    @Test
    public void givenSystemTime_whenCalled_thenGivesTimeinResult() {
        System.out.println(System.currentTimeMillis()); // Unix epoch in milli-seconds
        System.out.println(new Date(System.currentTimeMillis())); // human understandable format

        Assert.assertNotNull(System.currentTimeMillis());
        Assert.assertNotNull(new Date(System.currentTimeMillis()));
    }
}
