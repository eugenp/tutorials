package com.baeldung.java9.process;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProcessWaitTest {

    @Test
    void givenAProcess_whenUsingWaitFor_thenNoExceptionThrown() {
        // Code that interacts with a process should not throw IllegalMonitorStateException.
        assertDoesNotThrow(() -> {
            Process process = new ProcessBuilder("notepad.exe").start();
            int exitCode = process.waitFor();
        });
    }

    @Test
    void givenAProcess_whenUsingWaitWithoutSynchronization_thenExceptionThrown() {
        assertThrows(IllegalMonitorStateException.class, () -> {
            Process process = new ProcessBuilder("notepad.exe").start();
            process.wait();
        });
    }
}
