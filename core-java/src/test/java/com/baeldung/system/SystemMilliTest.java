package com.baeldung.system;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class SystemMilliTest {

    @Test
    public void givenSystemTime_whenCalled_thenGivesTimeinResult() {
        Assert.assertNotNull(System.currentTimeMillis());
        Assert.assertNotNull(new Date(System.currentTimeMillis()));
    }
}
