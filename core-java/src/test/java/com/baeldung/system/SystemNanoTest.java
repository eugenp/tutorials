package com.baeldung.system;

import org.junit.Assert;
import org.junit.Test;

public class SystemNanoTest {

    @Test
    public void givenSystem_whenCalledNanoTime_thenGivesTimeinResult() {
        Assert.assertNotNull(System.nanoTime());
        // do some other stuff
        Assert.assertNotNull(System.nanoTime());
    }
}
