package com.baeldung.java9.process;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.String;
import java.util.Optional;
import java.lang.Exception;
import java.lang.Integer;

import org.junit.jupiter.api.Test;

class ProcessUnderstandingTester {

    @Test
    public void compile_and_run_program_successfully() throws Exception {
        Process process = Runtime.getRuntime()
            .exec("javac -cp src src\\main\\java\\com\\baeldung\\java9\\process\\OutputStreamExample.java");
        process = Runtime.getRuntime()
            .exec("java -cp  src/main/java com.baeldung.java9.process.OutputStreamExample");
        BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
        int value = Integer.parseInt(output.readLine());
        assertEquals(3, value);
    }
    
    @Test
    public void fetch_input_from_subProcess() throws Exception {
        Process process = Runtime.getRuntime()
            .exec("javac -cp src src\\main\\java\\com\\baeldung\\java9\\process\\OutputStreamExample.java");
        process = Runtime.getRuntime()
            .exec("java -cp  src/main/java com.baeldung.java9.process.OutputStreamExample");
        BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
        int value = Integer.parseInt(output.readLine());
        assertEquals(3, value);
    }

    @Test
    public void fetch_Error_Stream_Output() throws Exception {
        Process process = Runtime.getRuntime()
            .exec("javac -cp src src\\main\\java\\com\\baeldung\\java9\\process\\ProcessCompilationError.java");
        BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String errorString = error.readLine();
        assertNotNull(errorString);
    }

    @Test
    public void successfully_create_process() throws Exception {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        assertNotNull(process);
    }
    
    @Test
    public void destroy_process_created_within_java_application() throws Exception{
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        Thread.sleep(10000);
        process.destroy();
    }
    
    @Test
    public void destroyForcibly_example() throws Exception{
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        Thread.sleep(10000);
        process.destroy();
    }

    @Test
    public void destroy_process_not_created_within_java_application() throws Exception{
        Optional<ProcessHandle> optionalProcessHandle = ProcessHandle.of(5232);
        ProcessHandle processHandle = optionalProcessHandle.get();
        processHandle.destroy();
    }
    
    @Test
    public void wait_For_example_successful() throws Exception{
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        assertThat(process.waitFor() >= 0);
    }
    
    @Test
    public void exitValue_example_successful() throws Exception{
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        assertThat(process.exitValue() >= 0);
    }
    
    @Test
    public void filter_active_process_based_on_range() throws Exception {
        assertThat(((int) ProcessHandle.allProcesses()
            .filter(ph -> (ph.pid() > 10000 && ph.pid() < 50000))
            .count()) > 0);
    }
}
