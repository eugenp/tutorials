package com.baeldung.java9.process;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by sanaulla on 2/23/2017.
 */

public class ProcessAPIEnhancementsUnitTest {

    Logger log = LoggerFactory.getLogger(ProcessAPIEnhancementsUnitTest.class);

    @Test
    public void givenCurrentProcess_whenInvokeGetInfo_thenSuccess() throws IOException {
        ProcessHandle processHandle = ProcessHandle.current();
        ProcessHandle.Info processInfo = processHandle.info();
        assertNotNull(processHandle.pid());
        assertEquals(true, processInfo.arguments()
          .isPresent());
        assertEquals(true, processInfo.command()
          .isPresent());
        assertTrue(processInfo.command()
          .get()
          .contains("java"));

        assertEquals(true, processInfo.startInstant()
          .isPresent());
        assertEquals(true, processInfo.totalCpuDuration()
          .isPresent());
        assertEquals(true, processInfo.user()
          .isPresent());
    }

    @Test
    public void givenSpawnProcess_whenInvokeGetInfo_thenSuccess() throws IOException {

        String javaCmd = ProcessUtils.getJavaCmd()
          .getAbsolutePath();
        ProcessBuilder processBuilder = new ProcessBuilder(javaCmd, "-version");
        Process process = processBuilder.inheritIO()
          .start();
        ProcessHandle processHandle = process.toHandle();
        ProcessHandle.Info processInfo = processHandle.info();
        assertNotNull(processHandle.pid());
        assertEquals(true, processInfo.arguments()
          .isPresent());
        assertEquals(true, processInfo.command()
          .isPresent());
        assertTrue(processInfo.command()
          .get()
          .contains("java"));
        assertEquals(true, processInfo.startInstant()
          .isPresent());
        assertEquals(false, processInfo.totalCpuDuration()
          .isPresent());
        assertEquals(true, processInfo.user()
          .isPresent());
    }

    @Test
    public void givenLiveProcesses_whenInvokeGetInfo_thenSuccess() {
        Stream<ProcessHandle> liveProcesses = ProcessHandle.allProcesses();
        liveProcesses.filter(ProcessHandle::isAlive)
            .forEach(ph -> {
                assertNotNull(ph.pid());
                assertEquals(true, ph.info()
                  .startInstant()
                  .isPresent());
                assertEquals(true, ph.info()
                  .user()
                  .isPresent());
            });
    }

    @Test
    public void givenProcess_whenGetChildProcess_thenSuccess() throws IOException {
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

    @Test
    public void givenProcess_whenAddExitCallback_thenSuccess() throws Exception {
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
        assertEquals(false, processHandle.isAlive());
        onProcessExit.thenAccept(ph -> {
            log.info("PID: {} has stopped", ph.pid());
        });
    }

}
