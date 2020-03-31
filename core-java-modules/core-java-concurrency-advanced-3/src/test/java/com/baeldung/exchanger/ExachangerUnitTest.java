package com.baeldung.exchanger;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.Exchanger;

import org.junit.Test;

public class ExachangerUnitTest {
    
    
    @Test
    public void givenThreads_whenMessageExchanged_thenCorrect() {
        Exchanger<String> exchanger = new Exchanger<>();

        Runnable taskA = () -> {
            try {
                String message = exchanger.exchange("from A");
                assertEquals("from B", message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable taskB = () -> {
            try {
                String message = exchanger.exchange("from B");
                assertEquals("from A", message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        new Thread(taskA).start();
        new Thread(taskB).start();
    }

    @Test
    public void givenThread_WhenExchangedMessage_thenCorrect() throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<>();

        Runnable runner = () -> {
            try {
                String message = exchanger.exchange("from runner");
                assertEquals("to runner", message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread producerThread = new Thread(runner);
        producerThread.start();
        String msg = exchanger.exchange("to runner");
        assertEquals("from runner", msg);
    }

}
