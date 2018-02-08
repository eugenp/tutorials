package com.baeldung.system;

import org.junit.Assert;
import org.junit.Test;

public class SystemOutTest {

    @Test
    public void givenSystem_whenCalledPrint_thenShowTextinResult() {
        System.out.print("some inline message"); // The next message will start on same line after
        System.out.print(" using print(). "); // The next message will start on same line after
        System.out.println("a message having new line at the end");

        Assert.assertNotNull(System.out);
    }
}
