package com.baeldung.junit.norunnablemethods;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class JUnit4IgnoreUnitTest {
    
    @Ignore
    @Test
    public void whenMethodIsIgnored_thenTestsDoNotRun() {
        Assert.assertTrue(true);
    }
}