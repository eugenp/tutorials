package com.baeldung.system;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class SystemInTest {

    @Test
    public void givenSystemin_whenCheckedForNull_thenNotNullinResult() throws IOException {
        Assert.assertNotNull(System.in);
    }
}
