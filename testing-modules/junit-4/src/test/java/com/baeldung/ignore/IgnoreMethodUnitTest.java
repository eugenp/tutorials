package com.baeldung.ignore;

import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

public class IgnoreMethodUnitTest {

    @Ignore("This method not ready yet")
    @Test
    public void whenMethodIsIgnored_thenTestsDoNotRun() {
        fail();
    }
}
