package com.baeldung.exceptions.illegalmonitorstate;

import com.google.common.util.concurrent.Uninterruptibles;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class IllegalMonitorStateExceptionUnitTest {

    @Test
    void whenSyncSenderAndSyncReceiverAreUsed_thenIllegalMonitorExceptionShouldNotBeThrown() throws InterruptedException {
        Data data = new Data();

        SynchronizedReceiver receiver = new SynchronizedReceiver(data);
        Thread receiverThread = new Thread(receiver, "receiver-thread");
        receiverThread.start();

        SynchronizedSender sender = new SynchronizedSender(data);
        Thread senderThread = new Thread(sender, "sender-thread");
        senderThread.start();

        senderThread.join(1000);
        receiverThread.join(1000);

        // we need to wait for enough time so that sender has had a chance to send the data
        assertTimeout(Duration.ofSeconds(10), () -> assertEquals("test", receiver.getMessage()));
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
