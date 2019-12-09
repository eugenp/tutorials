package com.baeldung.exitvshalt;

import org.junit.Test;

public class JvmExitDemoUnitTest {

    JvmExitAndHaltDemo jvmExitAndHaltDemo = new JvmExitAndHaltDemo();

    @Test
    public void givenProcessComplete_whenExitCalled_thenTriggerShutdownHook() {
        jvmExitAndHaltDemo.processAndExit();
    }

}
