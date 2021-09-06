package com.baeldung.ignore;

import static org.junit.Assert.fail;

import org.junit.Test;

public class IgnoredBadNameTestClass {

    @Test
    public void whenClassHasBadName_thenOnBuildTestsDoNotRun() {
        fail();
    }
}
