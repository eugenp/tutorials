package com.baeldung.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ChildThread extends Thread {

    private static Logger LOGGER = LoggerFactory.getLogger(ChildThread.class);

    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            LOGGER.error("InterruptedException caught!");
        }
    }
    
}

public class InterruptedExceptionExample {

    public static void main(String[] args) throws InterruptedException {
        ChildThread childThread = new ChildThread();
        childThread.start();
        childThread.interrupt();
    }
    
}
