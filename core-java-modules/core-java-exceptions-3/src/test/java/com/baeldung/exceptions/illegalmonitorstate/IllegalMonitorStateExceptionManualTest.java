package com.baeldung.exceptions.illegalmonitorstate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Needs to be run manually in order to demonstrate the IllegalMonitorStateException scenarios.
 *
 * There are some intermittent test failures in Jenkins that require further investigation.
 */
public class IllegalMonitorStateExceptionManualTest {

    @Test
    void whenSyncSenderAndSyncReceiverAreUsed_thenIllegalMonitorExceptionShouldNotBeThrown() throws InterruptedException {
        Data data = new Data();

        SynchronizedReceiver receiver = new SynchronizedReceiver(data);
        Thread receiverThread = new Thread(receiver, "receiver-thread");
        receiverThread.start();

        SynchronizedSender sender = new SynchronizedSender(data);
        Thread senderThread = new Thread(sender, "sender-thread");
        senderThread.start();

        // we need to wait for the sender and receiver threads to finish
        senderThread.join(10_000);
        receiverThread.join(10_000);

        assertEquals("test", receiver.getMessage());
        assertFalse(sender.hasIllegalMonitorStateExceptionOccurred());
        assertFalse(receiver.hasIllegalMonitorStateExceptionOccurred());
    }

    @Test
    void whenSyncSenderAndUnSyncReceiverAreUsed_thenIllegalMonitorExceptionShouldNotBeThrown() throws InterruptedException {
        Data data = new Data();

        UnsynchronizedReceiver receiver = new UnsynchronizedReceiver(data);
        Thread receiverThread = new Thread(receiver, "receiver-thread");
        receiverThread.start();

        SynchronizedSender sender = new SynchronizedSender(data);
        Thread senderThread = new Thread(sender, "sender-thread");
        senderThread.start();


        receiverThread.join(1000);
        senderThread.join(1000);

        assertNull(receiver.getMessage());
        assertFalse(sender.hasIllegalMonitorStateExceptionOccurred());
        assertTrue(receiver.hasIllegalMonitorStateExceptionOccurred());
    }

    @Test
    void whenUnSyncSenderAndSyncReceiverAreUsed_thenIllegalMonitorExceptionShouldBeThrown() throws InterruptedException {
        Data data = new Data();

        SynchronizedReceiver receiver = new SynchronizedReceiver(data);
        Thread receiverThread = new Thread(receiver, "receiver-thread");
        receiverThread.start();

        UnsynchronizedSender sender = new UnsynchronizedSender(data);
        Thread senderThread = new Thread(sender, "sender-thread");
        senderThread.start();


        receiverThread.join(1000);
        senderThread.join(1000);

        assertNull(receiver.getMessage());
        assertFalse(receiver.hasIllegalMonitorStateExceptionOccurred());
        assertTrue(sender.hasIllegalMonitorStateExceptionOccurred());
    }
}
