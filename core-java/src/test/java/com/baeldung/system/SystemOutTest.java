package com.baeldung.system;

import org.junit.Assert;
import org.junit.Test;

public class SystemOutTest {

    @Test
    public void givenSystem_whenCheckedForNull_thenNotNullinResult() {
        Assert.assertNotNull(System.out);
    }
}
