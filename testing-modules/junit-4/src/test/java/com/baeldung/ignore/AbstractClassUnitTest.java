package com.baeldung.ignore;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractClassUnitTest {

    @Before
    public void beforeSetUp() {
    }

    @Test
    public void whenClassIsAbstract_thenTestsNeedImpl() {
        fail();
    }

    @After
    public void afterSetDown() {
    }
}
