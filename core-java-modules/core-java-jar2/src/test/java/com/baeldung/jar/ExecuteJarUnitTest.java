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

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        InputStream inputStream = process.getInputStream();
        byte[] output = inputStream.readAllBytes();
        System.out.println("Output: " + new String(output));

        int exitCode = process.waitFor();
        Assert.assertEquals(0, exitCode);

    }

    private String getJarPath(String resourcePath) throws URISyntaxException {

        return new File(Objects.requireNonNull(getClass().getClassLoader()
            .getResource(resourcePath))
            .toURI()).getAbsolutePath();
    }

}
