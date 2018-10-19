package com.baeldung.java9.process;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProcessApiUnitTest {

    @Before
    public void init() {

    }

    @Test
    public void processInfoExample() throws NoSuchAlgorithmException {
        ProcessHandle self = ProcessHandle.current();
        long PID = self.getPid();
        ProcessHandle.Info procInfo = self.info();
        Optional<String[]> args = procInfo.arguments();
        Optional<String> cmd = procInfo.commandLine();
        Optional<Instant> startTime = procInfo.startInstant();
        Optional<Duration> cpuUsage = procInfo.totalCpuDuration();

        waistCPU();
        System.out.println("Args " + args);
        System.out.println("Command " + cmd.orElse("EmptyCmd"));
        System.out.println("Start time: " + startTime.get().toString());
        System.out.println(cpuUsage.get().toMillis());

        Stream<ProcessHandle> allProc = ProcessHandle.current().children();
        allProc.forEach(p -> {
            System.out.println("Proc " + p.getPid());
        });

    }

    @Test
    public void createAndDestroyProcess() throws IOException, InterruptedException {
        int numberOfChildProcesses = 5;
        for (int i = 0; i < numberOfChildProcesses; i++) {
            createNewJVM(ServiceMain.class, i).getPid();
        }

        Stream<ProcessHandle> childProc = ProcessHandle.current().children();
        assertEquals(childProc.count(), numberOfChildProcesses);

        childProc = ProcessHandle.current().children();
        childProc.forEach(processHandle -> {
            assertTrue("Process " + processHandle.getPid() + " should be alive!", processHandle.isAlive());
            CompletableFuture<ProcessHandle> onProcExit = processHandle.onExit();
            onProcExit.thenAccept(procHandle -> {
                System.out.println("Process with PID " + procHandle.getPid() + " has stopped");
            });
        });

        Thread.sleep(10000);

        childProc = ProcessHandle.current().children();
        childProc.forEach(procHandle -> {
            assertTrue("Could not kill process " + procHandle.getPid(), procHandle.destroy());
        });

        Thread.sleep(5000);

        childProc = ProcessHandle.current().children();
        childProc.forEach(procHandle -> {
            assertFalse("Process " + procHandle.getPid() + " should not be alive!", procHandle.isAlive());
        });

    }

    private Process createNewJVM(Class mainClass, int number) throws IOException {
        ArrayList<String> cmdParams = new ArrayList<String>(5);
        cmdParams.add(ProcessUtils.getJavaCmd().getAbsolutePath());
        cmdParams.add("-cp");
        cmdParams.add(ProcessUtils.getClassPath());
        cmdParams.add(mainClass.getName());
        cmdParams.add("Service " + number);
        ProcessBuilder myService = new ProcessBuilder(cmdParams);
        myService.inheritIO();
        return myService.start();
    }

    private void waistCPU() throws NoSuchAlgorithmException {
        ArrayList<Integer> randArr = new ArrayList<Integer>(4096);
        SecureRandom sr = SecureRandom.getInstanceStrong();
        Duration somecpu = Duration.ofMillis(4200L);
        Instant end = Instant.now().plus(somecpu);
        while (Instant.now().isBefore(end)) {
            // System.out.println(sr.nextInt());
            randArr.add(sr.nextInt());
        }
    }

}
