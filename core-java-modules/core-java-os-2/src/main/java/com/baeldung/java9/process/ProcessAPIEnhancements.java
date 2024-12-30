package com.baeldung.java9.process;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessAPIEnhancements {

    static Logger log = LoggerFactory.getLogger(ProcessAPIEnhancements.class);

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        infoOfCurrentProcess();
        infoOfLiveProcesses();
        infoOfSpawnProcess();
        infoOfExitCallback();
        infoOfChildProcess();
    }

    private static void infoOfCurrentProcess() {
        ProcessHandle processHandle = ProcessHandle.current();
        ProcessHandle.Info processInfo = processHandle.info();

        log.info("PID: " + processHandle.pid());
        log.info("Arguments: " + processInfo.arguments());
        log.info("Command: " + processInfo.command());
        log.info("Instant: " + processInfo.startInstant());
        log.info("Total CPU duration: " + processInfo.totalCpuDuration());
        log.info("User: " + processInfo.user());
    }

    private static void infoOfSpawnProcess() throws IOException {

        String javaCmd = ProcessUtils.getJavaCmd().getAbsolutePath();
        ProcessBuilder processBuilder = new ProcessBuilder(javaCmd, "-version");
        Process process = processBuilder.inheritIO().start();
        ProcessHandle processHandle = process.toHandle();
        ProcessHandle.Info processInfo = processHandle.info();

        log.info("PID: " + processHandle.pid());
        log.info("Arguments: " + processInfo.arguments());
        log.info("Command: " + processInfo.command());
        log.info("Instant: " + processInfo.startInstant());
        log.info("Total CPU duration: " + processInfo.totalCpuDuration());
        log.info("User: " + processInfo.user());
    }

    private static void infoOfLiveProcesses() {
        Stream<ProcessHandle> liveProcesses = ProcessHandle.allProcesses();
        liveProcesses.filter(ProcessHandle::isAlive)
            .forEach(ph -> {
                log.info("PID: " + ph.pid());
                log.info("Instance: " + ph.info().startInstant());
                log.info("User: " + ph.info().user());
            });
    }

    private static void infoOfChildProcess() throws IOException {
        int childProcessCount = 5;
        for (int i = 0; i < childProcessCount; i++) {
            String javaCmd = ProcessUtils.getJavaCmd()
              .getAbsolutePath();
            ProcessBuilder processBuilder
              = new ProcessBuilder(javaCmd, "-version");
            processBuilder.inheritIO().start();
        }

        Stream<ProcessHandle> children = ProcessHandle.current()
          .children();
        children.filter(ProcessHandle::isAlive)
          .forEach(ph -> log.info("PID: {}, Cmd: {}", ph.pid(), ph.info()
            .command()));
        Stream<ProcessHandle> descendants = ProcessHandle.current()
          .descendants();
        descendants.filter(ProcessHandle::isAlive)
          .forEach(ph -> log.info("PID: {}, Cmd: {}", ph.pid(), ph.info()
            .command()));
    }

    private static void infoOfExitCallback() throws IOException, InterruptedException, ExecutionException {
        String javaCmd = ProcessUtils.getJavaCmd()
          .getAbsolutePath();
        ProcessBuilder processBuilder
          = new ProcessBuilder(javaCmd, "-version");
        Process process = processBuilder.inheritIO()
          .start();
        ProcessHandle processHandle = process.toHandle();

        log.info("PID: {} has started", processHandle.pid());
        CompletableFuture<ProcessHandle> onProcessExit = processHandle.onExit();
        onProcessExit.get();
        log.info("Alive: " + processHandle.isAlive());
        onProcessExit.thenAccept(ph -> {
            log.info("PID: {} has stopped", ph.pid());
        });
    }

}
