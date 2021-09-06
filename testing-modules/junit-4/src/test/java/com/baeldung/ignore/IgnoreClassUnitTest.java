package com.baeldung.ignore;

import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

@Ignore("Test class not ready yet")
public class IgnoreClassUnitTest {

    @Test
    public void whenClassIsIgnored_thenTestsDoNotRun() {
        fail();
    }
}
