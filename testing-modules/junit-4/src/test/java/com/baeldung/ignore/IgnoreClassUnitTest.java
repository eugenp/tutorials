package com.baeldung.ignore;

import org.junit.Ignore;
import org.junit.Test;

@Ignore("Class not ready for tests")
public class IgnoreClassUnitTest {

    @Test
    public void whenDoTest_thenAssert() {

    }
}
