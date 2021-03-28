package com.baeldung.java9.process;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.String;
import java.util.concurrent.TimeUnit;
import java.lang.Integer;

import org.junit.jupiter.api.Test;

class ProcessUnderstandingUnitTest {

    @Test
    public void givenSubProcess_whenEncounteringError_thenErrorStreamNotNull() throws IOException {
        Process process = Runtime.getRuntime()
            .exec("javac -cp src src\\main\\java\\com\\baeldung\\java9\\process\\ProcessCompilationError.java");
        BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String errorString = error.readLine();
        assertNotNull(errorString);
    }

    //@Test - windows specific
    public void givenSubProcess_whenStarted_thenStartSuccessIsAlive() throws IOException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        assertTrue(builder.start().isAlive());
    }

    //@Test - windows specific
    public void givenSubProcess_whenDestroying_thenProcessNotAlive() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        Thread.sleep(10000);
        process.destroy();
        assertFalse(process.isAlive());
    }

    //@Test - windows specific
    public void givenSubProcess_whenAlive_thenDestroyForcibly() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        Thread.sleep(10000);
        process.destroy();
        if (process.isAlive()) {
            process.destroyForcibly();
        }
        assertFalse(process.isAlive());
    }

    //@Test - windows specific
    public void givenSubProcess_whenDestroyed_thenCheckIfAlive() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        Thread.sleep(10000);
        process.destroy();
        assertFalse(process.isAlive());
    }

    //@Test - windows specific
    public void givenSubProcess_whenCurrentThreadWaitsIndefinitelyuntilSubProcessEnds_thenProcessWaitForReturnsGrt0() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        assertThat(process.waitFor() >= 0);
    }

    //@Test - windows specific
    public void givenSubProcess_whenCurrentThreadWaitsAndSubProcessNotTerminated_thenProcessWaitForReturnsFalse() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        assertFalse(process.waitFor(1, TimeUnit.SECONDS));
    }

    //@Test - windows specific
    public void givenSubProcess_whenCurrentThreadWillNotWaitIndefinitelyforSubProcessToEnd_thenProcessExitValueReturnsGrt0() throws IOException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        assertThat(process.exitValue() >= 0);
    }

    @Test
    public void givenRunningProcesses_whenFilterOnProcessIdRange_thenGetSelectedProcessPid() {
        assertThat(((int) ProcessHandle.allProcesses()
            .filter(ph -> (ph.pid() > 10000 && ph.pid() < 50000))
            .count()) > 0);
    }

    @Test
    public void givenSourceProgram_whenReadingInputStream_thenFirstLineEquals3() throws IOException {

        Runtime.getRuntime()
                .exec("javac -cp src src/main/java/com/baeldung/java9/process/OutputStreamExample.java"
                        .replace("/", File.separator));

        Process process = Runtime.getRuntime()
                .exec("java -cp src/main/java com.baeldung.java9.process.OutputStreamExample"
                .replace("/", File.separator));

        BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
        int value = Integer.parseInt(output.readLine());

        assertEquals(3, value);
    }
}
