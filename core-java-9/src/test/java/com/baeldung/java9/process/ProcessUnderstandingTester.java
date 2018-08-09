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
    public void given_source_program_executed_successfully_from_another_program() throws IOException {
        Process process = Runtime.getRuntime()
            .exec("javac -cp src src\\main\\java\\com\\baeldung\\java9\\process\\OutputStreamExample.java");
        process = Runtime.getRuntime()
            .exec("java -cp  src/main/java com.baeldung.java9.process.OutputStreamExample");
        BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
        int value = Integer.parseInt(output.readLine());
        assertEquals(3, value);
    }
    
    @Test
    public void given_sub_process_fetch_output_successfully() throws IOException{
        Process process = Runtime.getRuntime()
            .exec("javac -cp src src\\main\\java\\com\\baeldung\\java9\\process\\OutputStreamExample.java");
        process = Runtime.getRuntime()
            .exec("java -cp  src/main/java com.baeldung.java9.process.OutputStreamExample");
        BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
        int value = Integer.parseInt(output.readLine());
        assertEquals(3, value);
    }

    @Test
    public void given_sub_process_fetch_error_output_stream_successfully() throws IOException {
        Process process = Runtime.getRuntime()
            .exec("javac -cp src src\\main\\java\\com\\baeldung\\java9\\process\\ProcessCompilationError.java");
        BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String errorString = error.readLine();
        assertNotNull(errorString);
    }

    @Test
    public void given_sub_process_start_successfully() throws IOException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        assertNotNull(process);
    }
    
    @Test
    public void given_subProcess_destroy_successfully() throws IOException, InterruptedException{
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        Thread.sleep(10000);
        process.destroy();
        assertNotNull(process);
    }
    
    @Test
    public void given_subProcess_destroy_forcibly() throws IOException, InterruptedException{
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        Thread.sleep(10000);
        process.destroy();
        assertNotNull(process);
    }

    @Test
    public void given_process_nt_created_from_within_java_application_destroy_successfully() {
        Optional<ProcessHandle> optionalProcessHandle = ProcessHandle.of(5232);
        ProcessHandle processHandle = optionalProcessHandle.get();
        processHandle.destroy();
        assertNotNull(processHandle);
    }
    
    @Test
    public void given_sub_process_main_thread_waits_indefinitely_until_sub_process_ends() throws IOException, InterruptedException{
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        assertThat(process.waitFor() >= 0);
    }
    
    @Test
    public void given_sub_process_main_thread_will_not_wait_indefinitely_for_sub_process_to_end() throws IOException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        assertThat(process.exitValue() >= 0);
    }
    
    @Test
    public void given_sub_processes_filter_based_on_process_id_range(){
        assertThat(((int) ProcessHandle.allProcesses()
            .filter(ph -> (ph.pid() > 10000 && ph.pid() < 50000))
            .count()) > 0);
    }
}
