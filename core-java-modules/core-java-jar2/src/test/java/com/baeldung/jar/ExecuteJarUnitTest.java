package com.baeldung.jar;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;

public class ExecuteJarUnitTest {

    private static final String RUNNABLE_JAR_PATH = "sample-jars/RunnableJar.jar";
    private static final String NON_RUNNABLE_JAR_PATH = "sample-jars/NonRunnableJar.jar";

    @Test
    public void givenRunnableJar_whenExecuted_thenShouldRunSuccessfully() throws Exception {

        String jarFile = getJarPath(RUNNABLE_JAR_PATH);

        ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", jarFile);

        Process process = processBuilder.start();

        InputStream inputStream = process.getInputStream();
        byte[] output = inputStream.readAllBytes();
        System.out.println("Output: " + new String(output));

        int exitCode = process.waitFor();
        Assert.assertEquals(0, exitCode);

    }

    @Test
    public void givenNonRunnableJar_whenExecutedWithMainClass_thenShouldRunSuccessfully() throws Exception {

        String jarFile = getJarPath(NON_RUNNABLE_JAR_PATH);

        String[] command = { "java", "-cp", jarFile, "com.company.HelloWorld" };

        Process process = Runtime.getRuntime()
            .exec(command);

        InputStream inputStream = process.getInputStream();
        InputStream errorStream = process.getErrorStream();

        byte[] output = inputStream.readAllBytes();
        System.out.println("Output: " + new String(output));

        byte[] error = errorStream.readAllBytes();
        if (error.length > 0) {
            System.err.println("Error: " + new String(error));
        }

        int exitCode = process.waitFor();
        Assert.assertEquals(0, exitCode);
    }

    private String getJarPath(String resourcePath) throws URISyntaxException {

        return new File(Objects.requireNonNull(getClass().getClassLoader()
            .getResource(resourcePath))
            .toURI()).getAbsolutePath();
    }

}
