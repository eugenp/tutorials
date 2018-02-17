package com.baeldung.system;

import org.junit.Test;

public class SystemFinalizeTest {

    @Override
    public void finalize() {
        // .. this will get called
    }

    @Test
    public void givenSystem_whenRunFinalization_thenRuns() {
        // do something
        System.runFinalization();
        // do something more
    }
}
