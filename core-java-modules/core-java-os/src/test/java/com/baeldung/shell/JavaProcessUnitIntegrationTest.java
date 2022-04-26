package com.baeldung.shell;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class JavaProcessUnitIntegrationTest {
    private static final boolean IS_WINDOWS = System.getProperty("os.name").toLowerCase().startsWith("windows");

    private static class StreamGobbler implements Runnable {
        private InputStream inputStream;
        private Consumer<String> consumer;

        public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumer);
        }
    }

    private Consumer<String> consumer = Assert::assertNotNull;

    private String homeDirectory = System.getProperty("user.home");

    private ExecutorService executorService;

    @Before
    public void setUp() {
        executorService = Executors.newSingleThreadExecutor();
    }

    @After
    public void tearDown() {
        executorService.shutdown();
    }

    @Test
    public void givenProcess_whenCreatingViaRuntime_shouldSucceed() throws Exception {
        Process process;
        if (IS_WINDOWS) {
            process = Runtime.getRuntime().exec(String.format("cmd.exe /c dir %s", homeDirectory));
        } else {
            process = Runtime.getRuntime().exec(String.format("sh -c ls %s", homeDirectory));
        }
        StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), consumer);

        Future<?> future = executorService.submit(streamGobbler);
        int exitCode = process.waitFor();

        // verify the stream output from the process
        assertDoesNotThrow(() -> future.get(10, TimeUnit.SECONDS));
        assertEquals(0, exitCode);
    }

    @Test
    public void givenProcess_whenCreatingViaProcessBuilder_shouldSucceed() throws Exception {
        ProcessBuilder builder = new ProcessBuilder();
        if (IS_WINDOWS) {
            builder.command("cmd.exe", "/c", "dir");
        } else {
            builder.command("sh", "-c", "ls");
        }
        builder.directory(new File(homeDirectory));
        Process process = builder.start();
        StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), consumer);

        Future<?> future = executorService.submit(streamGobbler);
        int exitCode = process.waitFor();

        // verify the stream output from the process
        assertDoesNotThrow(() -> future.get(10, TimeUnit.SECONDS));
        assertEquals(0, exitCode);
    }
}
