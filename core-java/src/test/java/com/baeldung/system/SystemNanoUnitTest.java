package com.baeldung.system;

import org.junit.Assert;
import org.junit.Test;

public class SystemNanoUnitTest {

    @Test
    public void givenSystem_whenCalledNanoTime_thenGivesTimeinResult() {
        long startTime = System.nanoTime();
        // do something that takes time
        long endTime = System.nanoTime();

        System.out.println("endTime:{}" + endTime);
        System.out.println("startTime:{}" + startTime);
        Assert.assertTrue(endTime - startTime < 10000);
    }
}
