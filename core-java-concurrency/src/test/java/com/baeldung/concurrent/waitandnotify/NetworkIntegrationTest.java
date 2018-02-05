package com.baeldung.concurrent.waitandnotify;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NetworkIntegrationTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private String expected;
    
    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }
    
    @Before
    public void setUpExpectedOutput() {
        StringWriter expectedStringWriter = new StringWriter();
        
        PrintWriter printWriter = new PrintWriter(expectedStringWriter);
        printWriter.println("First packet");
        printWriter.println("Second packet");
        printWriter.println("Third packet");
        printWriter.println("Fourth packet");
        printWriter.close();
        
        expected = expectedStringWriter.toString();
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }
    
    @Test
    public void givenSenderAndReceiver_whenSendingPackets_thenNetworkSynchronized() {
        Data data = new Data();
        Thread sender = new Thread(new Sender(data));
        Thread receiver = new Thread(new Receiver(data));
        
        sender.start();
        receiver.start();
        
        //wait for sender and receiver to finish before we test against expected
        try {
            sender.join();
            receiver.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); 
            System.out.println("Thread Interrupted");
        }
        
        assertEquals(expected, outContent.toString());
    }
}
