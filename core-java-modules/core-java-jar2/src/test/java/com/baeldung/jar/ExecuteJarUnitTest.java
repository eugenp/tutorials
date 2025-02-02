package com.baeldung.jar;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;

public class ExecuteJarUnitTest {

    private static final String RUNNABLE_JAR_PATH = "sample-jars/RunnableJar.jar";
    private static final String NON_RUNNABLE_JAR_PATH = "sample-jars/NonRunnableJar.jar";

    @Test
    public void givenRunnableJar_whenExecuted_thenShouldRunSuccessfully() {
        Process process = null;
        try {
            String jarFile = new File(Objects.requireNonNull(getClass().getClassLoader()
                .getResource(RUNNABLE_JAR_PATH))
                .toURI()).getAbsolutePath();

            ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", jarFile);
            processBuilder.redirectErrorStream(true);

            process = processBuilder.start();
            try (InputStream inputStream = process.getInputStream()) {
                byte[] output = inputStream.readAllBytes();
                System.out.println("Output: " + new String(output));
            }

            int exitCode = process.waitFor();
            Assert.assertEquals("Process exited with an unexpected exit code", 0, exitCode);
        } catch (IOException | InterruptedException | URISyntaxException e) {
            Assert.fail("Test failed due to exception: " + e.getMessage());
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    @Test
    public void givenNonRunnableJar_whenExecutedWithMainClass_thenShouldRunSuccessfully() {
        Process process = null;
        try {
            String jarFile = new File(Objects.requireNonNull(getClass().getClassLoader()
                .getResource(NON_RUNNABLE_JAR_PATH))
                .toURI()).getAbsolutePath();

            String[] command = { "java", "-cp", jarFile, "com.company.HelloWorld", "arg1", "arg2" };
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);

            process = processBuilder.start();  // Start process
            try (InputStream inputStream = process.getInputStream()) {
                byte[] output = inputStream.readAllBytes();
                System.out.println("Output: " + new String(output));
            }

            int exitCode = process.waitFor();
            Assert.assertEquals("Process exited with an unexpected exit code", 0, exitCode);
        } catch (IOException | InterruptedException | URISyntaxException e) {
            Assert.fail("Test failed due to exception: " + e.getMessage());
        } finally {
            if (process != null) {
                process.destroy();  // Ensure cleanup in all Java versions
            }
        }
    }

}
