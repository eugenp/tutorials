package com.baeldung.stackoverflowerror;

import static org.junit.Assert.fail;

import org.junit.Test;

public class CyclicDependancyUnitTest {
    @Test
    public void whenInstanciatingClassOne_thenThrowsException() {
        try {
            ClassOne obj = new ClassOne();
            fail();
        } catch (StackOverflowError soe) {
            soe.printStackTrace();
        }
    }
}
