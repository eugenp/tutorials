package com.baeldung.java.shell;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

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

    @Test
    public void givenProcess_whenCreatingViaRuntime_shouldSucceed() throws Exception {
        Process process;
        if (IS_WINDOWS) {
            process = Runtime.getRuntime().exec(String.format("cmd.exe /c dir %s", homeDirectory));
        } else {
            process = Runtime.getRuntime().exec(String.format("sh -c ls %s", homeDirectory));
        }
        StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), consumer);
        Executors.newSingleThreadExecutor().submit(streamGobbler);
        int exitCode = process.waitFor();
        Assert.assertEquals(0, exitCode);
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
        Executors.newSingleThreadExecutor().submit(streamGobbler);
        int exitCode = process.waitFor();
        Assert.assertEquals(0, exitCode);
    }
}
