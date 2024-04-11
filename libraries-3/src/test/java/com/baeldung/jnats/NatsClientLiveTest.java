package com.baeldung.jnats;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.ErrorListener;
import io.nats.client.JetStreamStatusException;
import io.nats.client.Message;
import io.nats.client.Nats;
import io.nats.client.Options;
import io.nats.client.Subscription;

/**
 * All the tests in this class require that a NATS server be running on localhost at the default port.
 * See {@link <a href="https://docs.nats.io/nats-server/installation">Installing a NATS Server</a>}.
 * <p>
 * IMPORTANT: Awaitility.await is used to simulate the real behavior
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
    public static final int WAIT_AT_MOST_MILLIS = 300;
    public static final int POLL_DELAY_MILLIS = 50;

    private Connection createConnection() throws IOException, InterruptedException {
        Options options = new Options.Builder().server("nats://localhost:4222")
            .connectionListener((connection, event) -> System.out.println("Connection Event: " + event.toString()))
            .errorListener(new CustomErrorListener())
            .build();
        return Nats.connect(options);
    }

    public static Connection createConnectionWithReportNoResponders() throws IOException, InterruptedException {
        Options options = new Options.Builder().server("nats://localhost:4222")
            .connectionListener((connection, event) -> System.out.println("Connection Event: " + event.toString()))
            .errorListener(new CustomErrorListener())
            .reportNoResponders()
            .build();
        return Nats.connect(options);
    }

    static class CustomErrorListener implements ErrorListener {
        @Override
        public void errorOccurred(Connection conn, String error) {
            System.err.println("Error Occurred: " + error);
        }

        @Override
        public void exceptionOccurred(Connection conn, Exception exp) {
            System.err.println("Exception Occurred: " + exp.toString());
        }
    }

    @Test
    public void givenNoSubscribers_whenPublishingMessages_thenItCannotBeDeterminedIfMessageIsReceived() {
        try (Connection connection = createConnection()) {
            connection.publish("mySubject", "myData".getBytes());
        }
        catch (Exception e) {
            fail("There should not be an exception");
        }
    }

    @Test
    public void givenNoSubscribersAndReportNoResponders_whenRequestReply_thenReceiveNoResponders() {
        try (Connection connection = createConnectionWithReportNoResponders()) {
            connection.publish("noOneIsSubscribed", null);
            CompletableFuture<Message> f = connection.request("noOneIsSubscribed", null);
            f.get(WAIT_AT_MOST_MILLIS, TimeUnit.MILLISECONDS);
            fail("There should be an exception");
        }
        catch (ExecutionException e) {
            Throwable cause = e.getCause();
            assertNotNull(cause);
            assertInstanceOf(JetStreamStatusException.class, cause);
            assertTrue(cause.getMessage().contains("503 No Responders Available For Request"));
        } catch (InterruptedException | TimeoutException | IOException e) {
            fail("There should not be one of these exceptions");
        }
    }

    @Test
    public void whenSubscribingSynchronouslyWithoutQueueGroups_thenEachSubscriptionShouldReceiveEachMessage() throws Exception {
        try (Connection connection = createConnection()) {
            Subscription sub1 = connection.subscribe("mySubject");
            Subscription sub2 = connection.subscribe("mySubject");

            connection.publish("mySubject", "data".getBytes());

            assertNotNull(sub1.nextMessage(TIMEOUT_MILLIS));
            assertNotNull(sub2.nextMessage(TIMEOUT_MILLIS));

            sub1.unsubscribe();
            sub2.unsubscribe();
        }
    }

    @Test
    public void whenSubscribingSynchronouslyWithQueueGroups_thenOnlyOneSubscriberInTheGroupShouldReceiveEachMessage() throws Exception {
        try (Connection connection = createConnection()) {
            Subscription qSub1 = connection.subscribe("mySubject", "myQueue");
            Subscription qSub2 = connection.subscribe("mySubject", "myQueue");

            connection.publish("mySubject", "data".getBytes());

            List<Message> messages = new ArrayList<>();

            Message message = qSub1.nextMessage(TIMEOUT_MILLIS);
            if (message != null) {
                messages.add(message);
            }

            message = qSub2.nextMessage(TIMEOUT_MILLIS);
            if (message != null) {
                messages.add(message);
            }

            assertEquals(1, messages.size());

            qSub1.unsubscribe();
            qSub2.unsubscribe();
        }
    }

    @Test
    public void whenSubscribingAsynchronouslyWithoutQueueGroups_thenEachMessageHandlerShouldReceiveEachMessage() throws Exception {
        try (Connection connection = createConnection()) {
            List<Message> messages1 = new ArrayList<>();
            List<Message> messages2 = new ArrayList<>();

            Dispatcher dispatcher = connection.createDispatcher();
            Subscription sub1 = dispatcher.subscribe("mySubject", messages1::add);
            Subscription sub2 = dispatcher.subscribe("mySubject", messages2::add);

            connection.publish("mySubject", "data1".getBytes());
            connection.publish("mySubject", "data2".getBytes());

            // Simulate real world time for messages to propagate.
            Awaitility.await()
                .atMost(WAIT_AT_MOST_MILLIS, TimeUnit.MILLISECONDS)
                .pollDelay(POLL_DELAY_MILLIS, TimeUnit.MILLISECONDS)
                .until(() -> messages1.size() + messages2.size() == 4);

            assertEquals(2, messages1.size());
            assertEquals(2, messages2.size());

            dispatcher.unsubscribe(sub1);
            dispatcher.unsubscribe(sub2);
        }
    }

    @Test
    public void whenSubscribingAsynchronouslyWithQueueGroups_thenOnlyOneMessageHandlerInTheGroupShouldReceiveEachMessage() throws Exception {
        try (Connection connection = createConnection()) {
            List<Message> messages = new ArrayList<>();

            Dispatcher dispatcher = connection.createDispatcher();
            Subscription qSub1 = dispatcher.subscribe("mySubject", "myQueue", messages::add);
            Subscription qSub2 = dispatcher.subscribe("mySubject", "myQueue", messages::add);

            connection.publish("mySubject", "data".getBytes());

            // Simulate real world time for messages to propagate.
            Awaitility.await()
                .atMost(WAIT_AT_MOST_MILLIS, TimeUnit.MILLISECONDS)
                .pollDelay(POLL_DELAY_MILLIS, TimeUnit.MILLISECONDS)
                .until(() -> messages.size() == 1);

            assertEquals(1, messages.size());
            dispatcher.unsubscribe(qSub1);
            dispatcher.unsubscribe(qSub2);
        }
    }

    @Test
    public void whenMessagesAreExchangedViaPublish_thenResponsesMustBeReceivedWithSecondarySubscription() throws Exception {
        try (Connection connection = createConnection()) {
            Subscription replySideSubscription1 = connection.subscribe("publishSubject");
            Subscription replySideSubscription2 = connection.subscribe("publishSubject");

            Subscription publishSideSubscription = connection.subscribe("replyToSubject");
            connection.publish("publishSubject", "replyToSubject", "Please respond!".getBytes());

            Message message = replySideSubscription1.nextMessage(TIMEOUT_MILLIS);
            assertNotNull(message, "No message!");
            assertEquals("Please respond!", new String(message.getData()));
            connection.publish(message.getReplyTo(), "Message Received By Subscription 1".getBytes());

            message = replySideSubscription2.nextMessage(TIMEOUT_MILLIS);
            assertNotNull(message, "No message!");
            assertEquals("Please respond!", new String(message.getData()));
            connection.publish(message.getReplyTo(), "Message Received By Subscription 2".getBytes());

            int responsesFrom1 = 0;
            int responsesFrom2 = 0;
            message = publishSideSubscription.nextMessage(TIMEOUT_MILLIS);
            assertNotNull(message, "No message!");
            String response = new String(message.getData());
            assertTrue(response.contains("Message Received"));
            if (response.contains("1")) {
                responsesFrom1++;
            }
            else if (response.contains("2")) {
                responsesFrom2++;
            }

            message = publishSideSubscription.nextMessage(TIMEOUT_MILLIS);
            assertNotNull(message, "No message!");
            response = new String(message.getData());
            assertTrue(response.contains("Message Received"));
            if (response.contains("1")) {
                responsesFrom1++;
            }
            else if (response.contains("2")) {
                responsesFrom2++;
            }

            assertEquals(1, responsesFrom1);
            assertEquals(1, responsesFrom2);

            replySideSubscription1.unsubscribe();
            replySideSubscription2.unsubscribe();
            publishSideSubscription.unsubscribe();
        }
    }

    @Test
    public void whenMessagesAreExchangedViaRequest_thenOnlyTheFirstResponseWillBeReceived() throws Exception {
        try (Connection connection = createConnection()) {
            Subscription replySideSubscription1 = connection.subscribe("requestSubject");
            Subscription replySideSubscription2 = connection.subscribe("requestSubject");

            CompletableFuture<Message> future = connection.request("requestSubject", "Please respond!".getBytes());

            Message message = replySideSubscription1.nextMessage(TIMEOUT_MILLIS);
            assertNotNull(message, "No message!");
            assertEquals("Please respond!", new String(message.getData()));
            connection.publish(message.getReplyTo(), "Message Received From Subscription 1".getBytes());

            message = replySideSubscription2.nextMessage(TIMEOUT_MILLIS);
            assertNotNull(message, "No message!");
            assertEquals("Please respond!", new String(message.getData()));
            connection.publish(message.getReplyTo(), "Message Received From Subscription 2".getBytes());

            message = future.get(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
            assertNotNull(message, "No message!");
            assertEquals("Message Received From Subscription 1", new String(message.getData()));

            replySideSubscription1.unsubscribe();
            replySideSubscription2.unsubscribe();
        }
    }

    @Test
    public void whenMatchingWildCardsAreUsedInSubscriptions_thenSubscriptionsMustReceiveAllMatchingMessages() throws Exception {
        try (Connection connection = createConnection()) {
            Subscription segmentStarSubscription = connection.subscribe("segment.*");

            connection.publish("segment.star", "hello segment star".getBytes());

            Message message = segmentStarSubscription.nextMessage(TIMEOUT_MILLIS);
            assertNotNull(message, "No message!");
            assertEquals("hello segment star", new String(message.getData()));

            Subscription segmentGreaterSubscription = connection.subscribe("segment.>");
            connection.publish("segment.greater.than", "hello segment greater".getBytes());

            message = segmentGreaterSubscription.nextMessage(TIMEOUT_MILLIS);
            assertNotNull(message, "No message!");
            assertEquals("hello segment greater", new String(message.getData()));

            segmentStarSubscription.unsubscribe();
            segmentGreaterSubscription.unsubscribe();
        }
    }

    @Test
    public void whenNonMatchingWildCardsAreUsedInSubscriptions_thenSubscriptionsMustNotReceiveNonMatchingMessages() throws Exception {
        try (Connection connection = createConnection()) {
            Subscription starSubscription = connection.subscribe("segment.*");

            connection.publish("segment.second.third", "hello there".getBytes());

            Message message = starSubscription.nextMessage(TIMEOUT_MILLIS);
            assertNull(message, "Got message!");

            Subscription greaterSubscription = connection.subscribe("segment.>");
            connection.publish("segment.second.third", "hello there".getBytes());

            message = greaterSubscription.nextMessage(TIMEOUT_MILLIS);
            assertNotNull(message, "No message!");
            assertEquals("hello there", new String(message.getData()));

            starSubscription.unsubscribe();
            greaterSubscription.unsubscribe();
        }
    }
}
