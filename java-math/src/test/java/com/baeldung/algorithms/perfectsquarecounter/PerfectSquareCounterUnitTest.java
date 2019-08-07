package com.baeldung.algorithms.perfectsquarecounter;

import org.junit.Assert;
import org.junit.Test;

public class PerfectSquareCounterUnitTest {

    @Test
    public void whenPass100_thenShouldCalculateCount() {
        int count = PerfectSquareCounter.calculateCount(100);
        Assert.assertEquals("Result not as expected", 10, count);
    }

}
