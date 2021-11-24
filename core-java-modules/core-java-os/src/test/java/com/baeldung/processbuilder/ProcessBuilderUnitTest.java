package com.baeldung.processbuilder;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ProcessBuilderUnitTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void givenProcessBuilder_whenInvokeStart_thenSuccess() throws IOException, InterruptedException, ExecutionException {
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-version");
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        List<String> results = readOutput(process.getInputStream());
        assertThat("Results should not be empty", results, is(not(empty())));
        assertThat("Results should contain java version: ", results, hasItem(containsString("version")));

        int exitCode = process.waitFor();
        assertEquals("No errors should be detected", 0, exitCode);
    }

    @Test
    public void givenProcessBuilder_whenModifyEnvironment_thenSuccess() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        Map<String, String> environment = processBuilder.environment();
        environment.forEach((key, value) -> System.out.println(key + value));

        environment.put("GREETING", "Hola Mundo");

        List<String> command = getGreetingCommand();
        processBuilder.command(command);
        Process process = processBuilder.start();

        List<String> results = readOutput(process.getInputStream());
        assertThat("Results should not be empty", results, is(not(empty())));
        assertThat("Results should contain a greeting ", results, hasItem(containsString("Hola Mundo")));

        int exitCode = process.waitFor();
        assertEquals("No errors should be detected", 0, exitCode);
    }

    @Test
    public void givenProcessBuilder_whenModifyWorkingDir_thenSuccess() throws IOException, InterruptedException {
        List<String> command = getDirectoryListingCommand();
        ProcessBuilder processBuilder = new ProcessBuilder(command);

        processBuilder.directory(new File("src"));
        Process process = processBuilder.start();

        List<String> results = readOutput(process.getInputStream());
        assertThat("Results should not be empty", results, is(not(empty())));
        assertThat("Results should contain directory listing: ", results, hasItems(containsString("main"), containsString("test")));

        int exitCode = process.waitFor();
        assertEquals("No errors should be detected", 0, exitCode);
    }

    @Test
    public void givenProcessBuilder_whenRedirectStandardOutput_thenSuccessWriting() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-version");

        processBuilder.redirectErrorStream(true);
        File log = tempFolder.newFile("java-version.log");
        processBuilder.redirectOutput(log);

        Process process = processBuilder.start();

        assertEquals("If redirected, should be -1 ", -1, process.getInputStream()
            .read());
        int exitCode = process.waitFor();
        assertEquals("No errors should be detected", 0, exitCode);

        List<String> lines = Files.lines(log.toPath())
            .collect(Collectors.toList());

        assertThat("Results should not be empty", lines, is(not(empty())));
        assertThat("Results should contain java version: ", lines, hasItem(containsString("version")));
    }

    @Test
    public void givenProcessBuilder_whenRedirectStandardOutput_thenSuccessAppending() throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("java", "-version");

        File log = tempFolder.newFile("java-version-append.log");
        processBuilder.redirectErrorStream(true);
        processBuilder.redirectOutput(Redirect.appendTo(log));

        Process process = processBuilder.start();

        assertEquals("If redirected output, should be -1 ", -1, process.getInputStream()
            .read());

        int exitCode = process.waitFor();
        assertEquals("No errors should be detected", 0, exitCode);

        List<String> lines = Files.lines(log.toPath())
            .collect(Collectors.toList());

        assertThat("Results should not be empty", lines, is(not(empty())));
        assertThat("Results should contain java version: ", lines, hasItem(containsString("version")));
    }

    @Test
    public void givenProcessBuilder_whenStartingPipeline_thenSuccess() throws IOException, InterruptedException {
        if (!isWindows()) {
            List<ProcessBuilder> builders = Arrays.asList(
                new ProcessBuilder("find", "src", "-name", "*.java", "-type", "f"), 
                new ProcessBuilder("wc", "-l"));

            List<Process> processes = ProcessBuilder.startPipeline(builders);
            Process last = processes.get(processes.size() - 1);

            List<String> output = readOutput(last.getInputStream());
            assertThat("Results should not be empty", output, is(not(empty())));
        }
    }
    
    @Test
    public void givenProcessBuilder_whenInheritIO_thenSuccess() throws IOException, InterruptedException {
        List<String> command = getEchoCommand();
        ProcessBuilder processBuilder = new ProcessBuilder(command);

        processBuilder.inheritIO();
        Process process = processBuilder.start();

        int exitCode = process.waitFor();
        assertEquals("No errors should be detected", 0, exitCode);
    }

    private List<String> readOutput(InputStream inputStream) throws IOException {
        try (BufferedReader output = new BufferedReader(new InputStreamReader(inputStream))) {
            return output.lines()
                .collect(Collectors.toList());
        }
    }

    private List<String> getDirectoryListingCommand() {
        return isWindows() ? Arrays.asList("cmd.exe", "/c", "dir") : Arrays.asList("/bin/sh", "-c", "ls");
    }

    private List<String> getGreetingCommand() {
        return isWindows() ? Arrays.asList("cmd.exe", "/c", "echo %GREETING%") : Arrays.asList("/bin/bash", "-c", "echo $GREETING");
    }

    private List<String> getEchoCommand() {
        return isWindows() ? Arrays.asList("cmd.exe", "/c", "echo hello") : Arrays.asList("/bin/sh", "-c", "echo hello");
    }

    private boolean isWindows() {
        return System.getProperty("os.name")
            .toLowerCase()
            .startsWith("windows");
    }

}
