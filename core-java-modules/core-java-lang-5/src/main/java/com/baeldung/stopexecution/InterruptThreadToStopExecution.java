package com.baeldung.stopexecution;

public class InterruptThreadToStopExecution extends Thread {
    @Override
    public void run() {
        while (!isInterrupted()) {
            if (isInterrupted()) {
                break;
            }
            // business logic
        }
    }
}
