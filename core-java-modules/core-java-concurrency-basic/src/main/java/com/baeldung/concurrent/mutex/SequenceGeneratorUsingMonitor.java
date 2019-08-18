package com.baeldung.concurrent.mutex;

import com.google.common.util.concurrent.Monitor;

public class SequenceGeneratorUsingMonitor extends SequenceGenerator {

    private Monitor monitor = new Monitor();

    @Override
    public int getNextSequence() throws InterruptedException {
        monitor.enter();
        try {
            return super.getNextSequence();
        } finally {
            monitor.leave();
        }
    }
}
