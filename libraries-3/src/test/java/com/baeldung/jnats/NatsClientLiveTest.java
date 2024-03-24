package com.baeldung.jnats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import io.nats.client.Message;
import io.nats.client.MessageHandler;
import io.nats.client.Subscription;

/**
 * All the tests in this class require that a NATS server be running on localhost at the default port.
 * See {@link <a href="https://docs.nats.io/nats-server/installation">Installing a NATS Server</a>}.
 * <p>
 * IMPORTANT: Using Thread.sleep() is not a generally recommended development concept.
 * In the case of tests, it is the easiest way to simulate the real behavior
 * that messages take some amount of time to go from being published
 * to being received. This amount of time will vary depending on many factors including:
 * <ul>
 *     <li>network latency</li>
 *     <li>server cluster configuration</li>
 *     <li>server computing power relative to the load of work</li>
 * </ul>
 * </p>
 */
public class NatsClientLiveTest {

    public static final int TIMEOUT_MILLIS = 200;
    public static final int WAIT_MILLIS = 300;

    private NatsClient connectClient() throws IOException, InterruptedException {
        return new NatsClient(NatsClient.createConnection("nats://localhost:4222"));
    }

    @Test
    public void whenSubscribingSynchronouslyWithoutQueueGroups_thenEachSubscriptionShouldReceiveEachMessage() throws Exception {
        try (NatsClient client = connectClient()) {
            List<Message> messages1 = new ArrayList<>();
            List<Message> messages2 = new ArrayList<>();

            Subscription sub1 = client.subscribeAsync("mySubject", messages1::add);
            Subscription sub2 = client.subscribeAsync("mySubject", messages2::add);

            client.publishMessage("mySubject", "data1");
            client.publishMessage("mySubject", "data2");

            // Simulate real world time for messages to propagate. See note at top about using Thread.sleep()
            Thread.sleep(WAIT_MILLIS); 

            assertEquals(2, messages1.size());
            assertEquals(2, messages2.size());

            client.unsubscribe(sub1);
            client.unsubscribe(sub2);
        }
    }

    @Test
    public void whenSubscribingSynchronouslyWithQueueGroups_thenOnlyOneSubscriberInTheGroupShouldReceiveEachMessage() throws Exception {
        try (NatsClient client = connectClient()) {
            Subscription qSub1 = client.subscribeSyncInQueueGroup("mySubject", "myQueue");
            Subscription qSub2 = client.subscribeSyncInQueueGroup("mySubject", "myQueue");

            client.publishMessage("mySubject", "data");

            List<Message> messages = new ArrayList<>();

            Message message = qSub2.nextMessage(TIMEOUT_MILLIS);
            if (message != null) {
                messages.add(message);
            }

            message = qSub1.nextMessage(TIMEOUT_MILLIS);
            if (message != null) {
                messages.add(message);
            }

            assertEquals(1, messages.size());

            client.unsubscribe(qSub1);
            client.unsubscribe(qSub2);
        }
    }

    @Test
    public void whenSubscribingAsynchronouslyWithoutQueueGroups_thenEachMessageHandlerShouldReceiveEachMessage() throws Exception {
        try (NatsClient client = connectClient()) {
            List<Message> messages1 = new ArrayList<>();
            List<Message> messages2 = new ArrayList<>();

            Subscription sub1 = client.subscribeAsync("mySubject", messages1::add);
            Subscription sub2 = client.subscribeAsync("mySubject", messages2::add);

            client.publishMessage("mySubject", "data1");
            client.publishMessage("mySubject", "data2");

            // Simulate real world time for messages to propagate. See note at top about using Thread.sleep()
            Thread.sleep(WAIT_MILLIS);

            assertEquals(2, messages1.size());
            assertEquals(2, messages2.size());

            client.unsubscribe(sub1);
            client.unsubscribe(sub2);
        }
    }

    @Test
    public void whenSubscribingAsynchronouslyWithQueueGroups_thenOnlyOneMessageHandlerInTheGroupShouldReceiveEachMessage() throws Exception {
        try (NatsClient client = connectClient()) {
            CountDownLatch awaitMessages = new CountDownLatch(1);
            List<Message> messages = new ArrayList<>();
            MessageHandler add = message -> {
                awaitMessages.countDown();
                messages.add(message);
            };

            Subscription qSub1 = client.subscribeAsyncInQueueGroup("mySubject", "myQueue", add);
            Subscription qSub2 = client.subscribeAsyncInQueueGroup("mySubject", "myQueue", add);
            client.publishMessage("mySubject", "data");
            awaitMessages.await();

            // Simulate real world time for messages to propagate. See note at top about using Thread.sleep()
            Thread.sleep(WAIT_MILLIS);
            
            assertEquals(1, messages.size());
            client.unsubscribe(qSub1);
            client.unsubscribe(qSub2);
        }
    }

    @Test
    public void whenMessagesAreExchangedViaPublish_thenResponsesMustBeReceivedWithSecondarySubscription() throws Exception {
        try (NatsClient client = connectClient()) {
            Subscription replySideSubscription = client.subscribeSync("requestSubject");
            Subscription publishSideSubscription = client.subscribeSync("replyToSubject");
            client.publishMessageWithReply("requestSubject", "replyToSubject", "hello there");

            Message message = replySideSubscription.nextMessage(TIMEOUT_MILLIS);
            assertNotNull(message, "No message!");
            assertEquals("hello there", new String(message.getData()));
            client.publishMessage(message.getReplyTo(), "hello back");

            message = publishSideSubscription.nextMessage(TIMEOUT_MILLIS);
            assertNotNull(message, "No message!");
            assertEquals("hello back", new String(message.getData()));

            client.unsubscribe(replySideSubscription);
            client.unsubscribe(publishSideSubscription);
        }
    }

    @Test
    public void whenMessagesAreExchangedViaRequest_thenResponsesMustBeReceivedDirectly() throws Exception {
        try (NatsClient client = connectClient()) {
            Subscription replySideSubscription = client.subscribeSync("requestSubject");

            CompletableFuture<Message> future = client.request("requestSubject", "hello there");

            Message message = replySideSubscription.nextMessage(TIMEOUT_MILLIS);
            assertNotNull(message, "No message!");
            assertEquals("hello there", new String(message.getData()));
            client.publishMessage(message.getReplyTo(), "hello back");

            message = future.get(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
            assertNotNull(message, "No message!");
            assertEquals("hello back", new String(message.getData()));

            client.unsubscribe(replySideSubscription);
        }
    }

    @Test
    public void whenMatchingWildCardsAreUsedInSubscriptions_thenSubscriptionsMustReceiveAllMatchingMessages() throws Exception {
        try (NatsClient client = connectClient()) {
            Subscription segmentStarSubscription = client.subscribeSync("segment.*");

            client.publishMessage("segment.star", "hello segment star");

            Message message = segmentStarSubscription.nextMessage(TIMEOUT_MILLIS);
            assertNotNull(message, "No message!");
            assertEquals("hello segment star", new String(message.getData()));

            Subscription segmentGreaterSubscription = client.subscribeSync("segment.>");
            client.publishMessage("segment.greater.than", "hello segment greater");

            message = segmentGreaterSubscription.nextMessage(TIMEOUT_MILLIS);
            assertNotNull(message, "No message!");
            assertEquals("hello segment greater", new String(message.getData()));

            client.unsubscribe(segmentGreaterSubscription);
        }
    }

    @Test
    public void whenNonMatchingWildCardsAreUsedInSubscriptions_thenSubscriptionsMustNotReceiveNonMatchingMessages() throws Exception {
        try (NatsClient client = connectClient()) {
            Subscription starSubscription = client.subscribeSync("segment.*");

            client.publishMessage("segment.second.third", "hello there");

            Message message = starSubscription.nextMessage(TIMEOUT_MILLIS);
            assertNull(message, "Got message!");

            Subscription greaterSubscription = client.subscribeSync("segment.>");
            client.publishMessage("segment.second.third", "hello there");

            message = greaterSubscription.nextMessage(TIMEOUT_MILLIS);
            assertNotNull(message, "No message!");
            assertEquals("hello there", new String(message.getData()));

            client.unsubscribe(starSubscription);
            client.unsubscribe(greaterSubscription);
        }
    }
}
