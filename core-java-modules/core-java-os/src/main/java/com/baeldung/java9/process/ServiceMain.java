package com.baeldung.java9.process;

import java.util.Optional;

public class ServiceMain {

    public static void main(String[] args) throws InterruptedException {
        ProcessHandle thisProcess = ProcessHandle.current();
        long pid = thisProcess.pid();

        Optional<String[]> opArgs = Optional.ofNullable(args);
        String procName = opArgs.map(str -> str.length > 0 ? str[0] : null).orElse(System.getProperty("sun.java.command"));

        for (int i = 0; i < 10000; i++) {
            System.out.println("Process " + procName + " with ID " + pid + " is running!");
            Thread.sleep(10000);
        }

    }

}
