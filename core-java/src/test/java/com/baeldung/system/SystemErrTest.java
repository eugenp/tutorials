package com.baeldung.system;

import org.junit.Assert;
import org.junit.Test;

public class SystemErrTest {

    @Test
    public void givenSystemErr_whenCheckedForNull_thenNotNullinResult() {
        Assert.assertNotNull(System.err);
    }
}
