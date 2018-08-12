package com.baeldung.java9.process;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.String;
import java.util.Optional;
import java.lang.Exception;
import java.lang.Integer;

import org.junit.jupiter.api.Test;

class ProcessUnderstandingTester {

    @Test
    public void givenSourceProgram_whenExecutedFromAnotherprogram_thenSuccess() throws IOException {
        Process process = Runtime.getRuntime()
            .exec("javac -cp src src\\main\\java\\com\\baeldung\\java9\\process\\OutputStreamExample.java");
        process = Runtime.getRuntime()
            .exec("java -cp  src/main/java com.baeldung.java9.process.OutputStreamExample");
        BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
        int value = Integer.parseInt(output.readLine());
        assertEquals(3, value);
    }
    
    @Test
    public void givenSubProcess_whenfetchOutput_thenSuccess() throws IOException{
        Process process = Runtime.getRuntime()
            .exec("javac -cp src src\\main\\java\\com\\baeldung\\java9\\process\\OutputStreamExample.java");
        process = Runtime.getRuntime()
            .exec("java -cp  src/main/java com.baeldung.java9.process.OutputStreamExample");
        BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
        int value = Integer.parseInt(output.readLine());
        assertEquals(3, value);
    }

    @Test
    public void givenSubProcess_whenErrorOutput_thenSuccess() throws IOException {
        Process process = Runtime.getRuntime()
            .exec("javac -cp src src\\main\\java\\com\\baeldung\\java9\\process\\ProcessCompilationError.java");
        BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String errorString = error.readLine();
        assertNotNull(errorString);
    }

    @Test
    public void givenSubProcess_thenStartSuccess() throws IOException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        assertNotNull(process);
    }
    
    @Test
    public void givenSubProcess_thenDestroySuccess() throws IOException, InterruptedException{
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        Thread.sleep(10000);
        process.destroy();
        assertNotNull(process);
    }
    
    @Test
    public void givenSubProcess_thenDestroyForciblySuccess() throws IOException, InterruptedException{
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        Thread.sleep(10000);
        process.destroy();
        if (process.isAlive()) {
            process.destroyForcibly();
        }
        assertNotNull(process);
    }

    @Test
    public void givenProcessNotCreated_fromWithinJavaApplication_thenDestroySuccessfully() {
        Optional<ProcessHandle> optionalProcessHandle = ProcessHandle.of(5232);
        ProcessHandle processHandle = optionalProcessHandle.get();
        processHandle.destroy();
        assertNotNull(processHandle);
    }
    
    @Test
    public void givenSubProcess_thenMainThreadWaitsIndefinitely_untilSubProcessEndsSuccessfully() throws IOException, InterruptedException{
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        assertThat(process.waitFor() >= 0);
    }
    
    @Test
    public void givenSubProcess_thenMainThreadWaitsWillNotIndefinitely_forSubProcessToEndSuccessfully() throws IOException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        assertThat(process.exitValue() >= 0);
    }
    
    @Test
    public void givenSubProcess_thenFilterOnProcessIdRange_Successfully(){
        assertThat(((int) ProcessHandle.allProcesses()
            .filter(ph -> (ph.pid() > 10000 && ph.pid() < 50000))
            .count()) > 0);
    }
}
