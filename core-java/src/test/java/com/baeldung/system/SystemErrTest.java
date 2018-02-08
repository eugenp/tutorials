package com.baeldung.system;

import org.junit.Assert;
import org.junit.Test;

public class SystemErrTest {

    @Test
    public void givenSystemErr_whenCheckedForNull_thenNotNullinResult() {
        System.err.print("some inline error message"); // The next message will start on same line after
        System.err.print(" using print(). "); // The next message will start on same line after
        System.err.println("an error message having new line at the end");

        Assert.assertNotNull(System.err);
    }
}
