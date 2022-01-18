package com.baeldung.ignore;

import org.junit.Ignore;
import org.junit.Test;

public class IgnoreMethodUnitTest {

    @Ignore("This test method not ready yet")
    @Test
    public void whenMethodIsIgnored_thenTestsDoNotRun() {

    }
}
