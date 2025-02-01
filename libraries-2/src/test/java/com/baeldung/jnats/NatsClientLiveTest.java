package com.baeldung.jnats;

import io.nats.client.*;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * All the tests in this class require that a NATS server be running on localhost at the default port.
 * See {@link <a href="https://docs.nats.io/nats-server/installation">Installing a NATS Server</a>}.
 * <p>
 * IMPORTANT: Awaitility.await is used to account for the real behavior
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

    private final static Logger log = LoggerFactory.getLogger(NatsClientLiveTest.class);

    private static final int TIMEOUT_MILLIS = 200;
    private static final int WAIT_AT_MOST_MILLIS = 300;
    private static final int POLL_DELAY_MILLIS = 50;

    private static Connection createConnection() throws IOException, InterruptedException {
        return createConnection("nats://localhost:4222");
    }

    private static Connection createConnection(String uri) throws IOException, InterruptedException {
        Options options = new Options.Builder().server(uri)
            .connectionListener((connection, event) -> log.info("Connection Event: " + event))
            .errorListener(new CustomErrorListener())
            .build();
        return Nats.connect(options);
    }

    public static Connection createConnectionWithReportNoResponders() throws IOException, InterruptedException {
        return createConnectionWithReportNoResponders("nats://localhost:4222");
    }

    public static Connection createConnectionWithReportNoResponders(String uri) throws IOException, InterruptedException {
        Options options = new Options.Builder().server(uri)
            .connectionListener((connection, event) -> log.info("Connection Event: " + event))
            .errorListener(new CustomErrorListener())
            .reportNoResponders()
            .build();
        return Nats.connect(options);
    }

    private static String convertMessageDataBytesToString(Message message) {
        return new String(message.getData(), StandardCharsets.UTF_8);
    }

    private static byte[] convertStringToBytes(String s) {
        return s.getBytes(StandardCharsets.UTF_8);
    }

    static class CustomErrorListener implements ErrorListener {

        @Override
        public void errorOccurred(Connection conn, String error) {
            log.error("Error Occurred: " + error);
        }

        @Override
        public void exceptionOccurred(Connection conn, Exception exp) {
            log.error("Exception Occurred: " + exp);
        }
    }

    @Test
    public void givenNoSubscribers_whenPublishingMessages_thenItCannotBeDeterminedIfMessageIsReceived() {
        try (Connection natsConnection = createConnection()) {
            natsConnection.publish("mySubject", convertStringToBytes("myData"));
        } catch (Exception e) {
            fail("There should not be an exception");
        }
    }

    @Test
    public void givenNoSubscribersAndReportNoResponders_whenRequestReply_thenReceiveNoResponders() {
        try (Connection natsConnection = createConnectionWithReportNoResponders()) {
            natsConnection.publish("noOneIsSubscribed", null);
            CompletableFuture<Message> future = natsConnection.request("noOneIsSubscribed", null);
            future.get(WAIT_AT_MOST_MILLIS, TimeUnit.MILLISECONDS);
            fail("There should be an exception");
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            assertNotNull(cause);
            assertInstanceOf(JetStreamStatusException.class, cause);
            assertTrue(cause.getMessage()
                .contains("503 No Responders Available For Request"));
        } catch (InterruptedException | TimeoutException | IOException e) {
            fail("There should not be one of these exceptions");
        }
    }

    @Test
    public void whenSubscribingSynchronouslyWithoutQueueGroups_thenEachSubscriptionShouldReceiveEachMessage() throws Exception {
        try (Connection natsConnection = createConnection()) {
            Subscription subscription1 = natsConnection.subscribe("mySubject");
            Subscription subscription2 = natsConnection.subscribe("mySubject");

            natsConnection.publish("mySubject", convertStringToBytes("data"));

            assertNotNull(subscription1.nextMessage(TIMEOUT_MILLIS));
            assertNotNull(subscription2.nextMessage(TIMEOUT_MILLIS));

            subscription1.unsubscribe();
            subscription2.unsubscribe();
        }
    }

    @Test
    public void whenSubscribingSynchronouslyWithQueueGroups_thenOnlyOneSubscriberInTheGroupShouldReceiveEachMessage() throws Exception {
        try (Connection natsConnection = createConnection()) {
            Subscription qSub1 = natsConnection.subscribe("mySubject", "myQueue");
            Subscription qSub2 = natsConnection.subscribe("mySubject", "myQueue");

            natsConnection.publish("mySubject", convertStringToBytes("data"));

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
        try (Connection natsConnection = createConnection()) {
            List<Message> messages1 = new ArrayList<>();
            List<Message> messages2 = new ArrayList<>();

            Dispatcher dispatcher = natsConnection.createDispatcher();
            Subscription subscription1 = dispatcher.subscribe("mySubject", messages1::add);
            Subscription subscription2 = dispatcher.subscribe("mySubject", messages2::add);

            natsConnection.publish("mySubject", convertStringToBytes("data1"));
            natsConnection.publish("mySubject", convertStringToBytes("data2"));

            // wait for messages to propagate.
            Awaitility.await()
                .atMost(WAIT_AT_MOST_MILLIS, TimeUnit.MILLISECONDS)
                .pollDelay(POLL_DELAY_MILLIS, TimeUnit.MILLISECONDS)
                .until(() -> messages1.size() + messages2.size() == 4);

            assertEquals(2, messages1.size());
            assertEquals(2, messages2.size());

            dispatcher.unsubscribe(subscription1);
            dispatcher.unsubscribe(subscription2);
        }
    }

    @Test
    public void whenSubscribingAsynchronouslyWithQueueGroups_thenOnlyOneMessageHandlerInTheGroupShouldReceiveEachMessage() throws Exception {
        try (Connection natsConnection = createConnection()) {
            List<Message> messages = new ArrayList<>();
            Dispatcher dispatcher = natsConnection.createDispatcher();
            Subscription qSub1 = dispatcher.subscribe("mySubject", "myQueue", messages::add);
            Subscription qSub2 = dispatcher.subscribe("mySubject", "myQueue", messages::add);

            natsConnection.publish("mySubject", convertStringToBytes("data"));

            // wait for messages to propagate.
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
        try (Connection natsConnection = createConnection()) {
            Subscription replySideSubscription0 = natsConnection.subscribe("publishSubject");
            Subscription replySideSubscription1 = natsConnection.subscribe("publishSubject");

            Subscription publishSideSubscription = natsConnection.subscribe("replyToSubject");
            natsConnection.publish("publishSubject", "replyToSubject", convertStringToBytes("Please respond!"));

            Message message = replySideSubscription0.nextMessage(TIMEOUT_MILLIS);
            assertNotNull(message, "No message!");
            assertEquals("Please respond!", convertMessageDataBytesToString(message));
            natsConnection.publish(message.getReplyTo(), convertStringToBytes("Message Received By Subscription 0"));

            message = replySideSubscription1.nextMessage(TIMEOUT_MILLIS);
            assertNotNull(message, "No message!");
            assertEquals("Please respond!", convertMessageDataBytesToString(message));
            natsConnection.publish(message.getReplyTo(), convertStringToBytes("Message Received By Subscription 1"));

            int[] responsesFrom = new int[2];
            message = publishSideSubscription.nextMessage(TIMEOUT_MILLIS);
            assertNotNull(message, "No message!");
            int replierIndex = extractReplierIndexFromMessageData(message);
            responsesFrom[replierIndex]++;

            message = publishSideSubscription.nextMessage(TIMEOUT_MILLIS);
            assertNotNull(message, "No message!");
            replierIndex = extractReplierIndexFromMessageData(message);
            responsesFrom[replierIndex]++;

            assertEquals(1, responsesFrom[0]);
            assertEquals(1, responsesFrom[1]);

            replySideSubscription0.unsubscribe();
            replySideSubscription1.unsubscribe();
            publishSideSubscription.unsubscribe();
        }
    }

    private int extractReplierIndexFromMessageData(Message message) {
        String messageData = convertMessageDataBytesToString(message);
        assertTrue(messageData.contains("Message Received By Subscription"));
        return messageData.endsWith("0") ? 0 : 1;
    }

    @Test
    public void whenMessagesAreExchangedViaRequest_thenOnlyTheFirstResponseWillBeReceived() throws Exception {
        try (Connection natsConnection = createConnection()) {
            Subscription replySideSubscription1 = natsConnection.subscribe("requestSubject");
            Subscription replySideSubscription2 = natsConnection.subscribe("requestSubject");

            CompletableFuture<Message> future = natsConnection.request("requestSubject", convertStringToBytes("Please respond!"));

            Message message = replySideSubscription1.nextMessage(TIMEOUT_MILLIS);
            assertNotNull(message, "No message!");
            assertEquals("Please respond!", convertMessageDataBytesToString(message));
            natsConnection.publish(message.getReplyTo(), convertStringToBytes("Message Received From Subscription 1"));

            message = replySideSubscription2.nextMessage(TIMEOUT_MILLIS);
            assertNotNull(message, "No message!");
            assertEquals("Please respond!", convertMessageDataBytesToString(message));
            natsConnection.publish(message.getReplyTo(), convertStringToBytes("Message Received From Subscription 2"));

            message = future.get(TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
            assertNotNull(message, "No message!");
            assertEquals("Message Received From Subscription 1", convertMessageDataBytesToString(message));

            replySideSubscription1.unsubscribe();
            replySideSubscription2.unsubscribe();
        }
    }

    @Test
    public void whenMatchingWildCardsAreUsedInSubscriptions_thenSubscriptionsMustReceiveAllMatchingMessages() throws Exception {
        try (Connection natsConnection = createConnection()) {
            Subscription segmentStarSubscription = natsConnection.subscribe("segment.*");

            natsConnection.publish("segment.another", convertStringToBytes("hello segment star sub"));

            Message message = segmentStarSubscription.nextMessage(TIMEOUT_MILLIS);
            assertNotNull(message, "No message!");
            assertEquals("hello segment star sub", convertMessageDataBytesToString(message));

            Subscription segmentGreaterSubscription = natsConnection.subscribe("segment.>");
            natsConnection.publish("segment.one.two", convertStringToBytes("hello segment greater sub"));

            message = segmentGreaterSubscription.nextMessage(TIMEOUT_MILLIS);
            assertNotNull(message, "No message!");
            assertEquals("hello segment greater sub", convertMessageDataBytesToString(message));

            segmentStarSubscription.unsubscribe();
            segmentGreaterSubscription.unsubscribe();
        }
    }

    @Test
    public void whenNonMatchingWildCardsAreUsedInSubscriptions_thenSubscriptionsMustNotReceiveNonMatchingMessages() throws Exception {
        try (Connection natsConnection = createConnection()) {
            Subscription starSubscription = natsConnection.subscribe("segment.*");

            natsConnection.publish("segment.second.third", convertStringToBytes("hello there"));

            Message message = starSubscription.nextMessage(TIMEOUT_MILLIS);
            assertNull(message, "Got message!");

            Subscription greaterSubscription = natsConnection.subscribe("segment.>");
            natsConnection.publish("segment.second.third", convertStringToBytes("hello there"));

            message = greaterSubscription.nextMessage(TIMEOUT_MILLIS);
            assertNotNull(message, "No message!");
            assertEquals("hello there", convertMessageDataBytesToString(message));

            starSubscription.unsubscribe();
            greaterSubscription.unsubscribe();
        }
    }
}
