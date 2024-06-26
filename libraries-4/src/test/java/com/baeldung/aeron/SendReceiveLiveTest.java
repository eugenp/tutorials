package com.baeldung.aeron;

import io.aeron.Aeron;
import io.aeron.CommonContext;
import io.aeron.ConcurrentPublication;
import io.aeron.Subscription;
import io.aeron.driver.MediaDriver;
import io.aeron.logbuffer.FragmentHandler;
import org.agrona.BufferUtil;
import org.agrona.concurrent.BackoffIdleStrategy;
import org.agrona.concurrent.IdleStrategy;
import org.agrona.concurrent.UnsafeBuffer;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class SendReceiveLiveTest {
    private static final String CHANNEL = "aeron:udp?endpoint=localhost:20121";
    private static final int STREAM = 1001;

    @Test
    void send() throws InterruptedException {
        MediaDriver mediaDriver = MediaDriver.launchEmbedded();
        try (Aeron aeron = Aeron.connect(new Aeron.Context().aeronDirectoryName(mediaDriver.aeronDirectoryName()))) {
            ConcurrentPublication publication = aeron.addPublication(CHANNEL, STREAM);
            while (!publication.isConnected()) {
                TimeUnit.MILLISECONDS.sleep(100);
            }

            for (int i = 0; i < 10; ++i) {
                String message = "Hello, World: " + i + " at " + System.currentTimeMillis();

                UnsafeBuffer buffer = new UnsafeBuffer(BufferUtil.allocateDirectAligned(256, 64));
                buffer.putStringWithoutLengthUtf8(0, message);

                long offer = publication.offer(buffer, 0, message.length());
                System.out.printf("Offered message <<%s>> with result %d%n", message, offer);
            }
        }
    }

    @Test
    void receive() {
        MediaDriver mediaDriver = MediaDriver.launchEmbedded();
        try (Aeron aeron = Aeron.connect(new Aeron.Context().aeronDirectoryName(mediaDriver.aeronDirectoryName()))) {
            Subscription subscription = aeron.addSubscription(CHANNEL, STREAM);

            IdleStrategy idleStrategy = new BackoffIdleStrategy(100, 10,
                    TimeUnit.MICROSECONDS.toNanos(1), TimeUnit.MICROSECONDS.toNanos(100));

            FragmentHandler fragmentHandler = (buffer, offset, length, header) ->
            {
                String data = buffer.getStringWithoutLengthUtf8(offset, length);

                System.out.printf("Message to stream %d from session %d (%d@%d) <<%s>> at %d%n",
                        STREAM, header.sessionId(), length, offset, data, System.currentTimeMillis());
            };

            while (true) {
                int fragmentsRead = subscription.poll(fragmentHandler, 10);
                idleStrategy.idle(fragmentsRead);
            }
        }
    }
}
