package com.baeldung.volatilekeywordthreadsafety;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VolatileVarNotThreadSafeUnitTest {

    @Test
    public void whenCalledMainMethod_thenIncrementCount() throws InterruptedException {
        VolatileVarNotThreadSafe.main(null);
        Assertions.assertTrue(VolatileVarNotThreadSafe.getCount() > 0);
    }
}
