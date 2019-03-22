package com.baeldung.java9.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcessUnderstanding {

    public static int compileAndRunJavaProgram() throws IOException {
        Process process = Runtime.getRuntime()
            .exec("javac -cp src src\\main\\java\\com\\baeldung\\java9\\process\\OutputStreamExample.java");
        process = Runtime.getRuntime()
            .exec("java -cp  src/main/java com.baeldung.java9.process.OutputStreamExample");
        BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
        int value = Integer.parseInt(output.readLine());
        return value;
    }

    public static String getErrorStreamExample() throws IOException {
        Process process = Runtime.getRuntime()
            .exec("javac -cp src src\\main\\java\\com\\baeldung\\java9\\process\\ProcessCompilationError.java");
        BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String errorString = error.readLine();
        return errorString;
    }

    public static void creatingNewProcess() throws IOException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
    }

    public static int filterProcessWithStreamsInSpecificRangeReturnCount() {
        return (int) ProcessHandle.allProcesses()
            .filter(ph -> (ph.pid() > 10000 && ph.pid() < 50000))
            .count();
    }

    public static void destroyingProcessCreatedBySameProcess() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        Thread.sleep(10000);
        process.destroy();
    }

    public static void destroyingProcessCreatedByDifferentProcess() {
        // find out the process id of current running task by checking
        // task manager in windows and enter the integer value
        Optional<ProcessHandle> optionalProcessHandle = ProcessHandle.of(5232);
        ProcessHandle processHandle = optionalProcessHandle.get();
        processHandle.destroy();
    }

    public static int waitForExample() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        return process.waitFor();
    }

    public static int exitValueExample() throws IOException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        process.destroy();
        return process.exitValue();
    }

    public static void destroyExample() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        Thread.sleep(10000);
        process.destroy();
    }

    public static void destroyForciblyExample() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("notepad.exe");
        Process process = builder.start();
        Thread.sleep(10000);
        process.destroy();
        if (process.isAlive()) {
            process.destroyForcibly();
        }
    }

    public static void outputStreamDemo() throws IOException, InterruptedException {
        Logger log = Logger.getLogger(ProcessUnderstanding.class.getName());
        Process pr = Runtime.getRuntime()
            .exec("javac -cp src src\\main\\java\\com\\baeldung\\java9\\process\\ChildProcess.java");
        final Process process = Runtime.getRuntime()
            .exec("java -cp  src/main/java com.baeldung.java9.process.ChildProcess");
        try (Writer w = new OutputStreamWriter(process.getOutputStream(), "UTF-8")) {
            w.write("send to child\n");
        }
        new Thread(() -> {
            try {
                int c;
                while ((c = process.getInputStream()
                    .read()) != -1)
                    System.out.write((byte) c);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        // send to child
        log.log(Level.INFO, "rc=" + process.waitFor());
    }
}
