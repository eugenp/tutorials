package com.baeldung.system;

import org.junit.Assert;
import org.junit.Test;

import java.io.Console;

public class SystemConsoleTest {

    @Test
    public void givenConsole_whenCheckedNull_thenGiveStatusinResult() {
        Console console = System.console();

        if (console != null) {
            Assert.assertNotNull(console);
        } else {
            Assert.assertNull(console);
        }
    }
}
