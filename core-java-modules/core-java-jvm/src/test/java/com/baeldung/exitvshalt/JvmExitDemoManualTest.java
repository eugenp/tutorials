package com.baeldung.exitvshalt;

import org.junit.Test;

public class JvmExitDemoManualTest {

    JvmExitAndHaltDemo jvmExitAndHaltDemo = new JvmExitAndHaltDemo();

    @Test
    public void givenProcessComplete_whenExitCalled_thenTriggerShutdownHook() {
        jvmExitAndHaltDemo.processAndExit();
    }

}
