package com.baeldung.exitvshalt;

import org.junit.Test;

public class JvmHaltDemoUnitTest {

    JvmExitAndHaltDemo jvmExitAndHaltDemo = new JvmExitAndHaltDemo();

    @Test
    public void givenProcessComplete_whenHaltCalled_thenDoNotTriggerShutdownHook() {
        jvmExitAndHaltDemo.processAndHalt();
    }

}
