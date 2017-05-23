package com.baeldung.stackoverflowerror;

import static org.junit.Assert.fail;
import org.junit.Test;

public class CyclicDependancyManualTest {
    @Test(expected = StackOverflowError.class)
    public void whenInstanciatingClassOne_thenThrowsException() {
            ClassOne obj = new ClassOne();
    }
}
