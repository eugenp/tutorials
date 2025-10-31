package com.baeldung.enginetestkit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import enginetestkit.Display;
import enginetestkit.Platform;

@Ignore
public class DisplayTest {

    private final Display display = new Display(Platform.DESKTOP, 1000);

    @Test
    void whenCorrect_thenSucceeds() {
        assertEquals(1000, display.getHeight());
    }

    @Test
    void whenIncorrect_thenFails() {
        assertEquals(500, display.getHeight());
    }

    @Test
    @Disabled("Flakey test needs investigating")
    void whenDisabled_thenSkips() {
        assertEquals(999, display.getHeight());
    }

    @Test
    void whenAssumptionsFail_thenAborts() {
        assumeTrue(display.getPlatform() == Platform.MOBILE, "test only runs for mobile");
    }

}
