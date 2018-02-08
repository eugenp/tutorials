package com.baeldung.system;

import org.junit.Assert;
import org.junit.Test;

public class SystemNanoTest {

    @Test
    public void givenSystem_whenCalledNanoTime_thenGivesTimeinResult() {
        System.out.println(System.nanoTime());
        // do some other stuff
        System.out.println(System.nanoTime());

        Assert.assertNotNull(System.nanoTime());
    }
}
