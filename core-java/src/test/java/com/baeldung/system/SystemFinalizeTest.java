package com.baeldung.system;

import org.junit.Test;

public class SystemFinalizeTest {

    @Test
    public void givenSystem_whenRunFinalization_thenRuns() {
        // do something
        System.runFinalization();
        // do something more
    }
}
