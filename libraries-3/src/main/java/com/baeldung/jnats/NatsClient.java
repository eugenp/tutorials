package com.baeldung.jnats;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import io.nats.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NatsClient {

    private final String serverURI;

    private final Map<Subscription, Dispatcher> dispatcherBySubscription = new HashMap<>();

    private final static Logger log = LoggerFactory.getLogger(NatsClient.class);

    private Connection natsConnection;

    NatsClient() {
        this(null);
    }

    public NatsClient(String serverURI) {
        if (serverURI == null || serverURI.isEmpty()) {
            this.serverURI = "nats://localhost:4222";
        }
        else {
            this.serverURI = serverURI;
        }
        initConnection();
    }

    static class CustomErrorListener implements ErrorListener {
        @Override
        public void errorOccurred(Connection conn, String error) {
            log.error("Error Occurred: {}", error);
        }

        @Override
        public void exceptionOccurred(Connection conn, Exception exp) {
            log.error("Exception Occurred: {}", exp.toString());
        }
    }
    private void initConnection() {
        try {
            Options options = new Options.Builder()
                .server(serverURI)
                .connectionListener((connection, event) -> log.info("Connection Event: {}", event.toString()))
                .errorListener(new CustomErrorListener())
                .build();
            natsConnection = Nats.connect(options);
        } catch (IOException | InterruptedException e) {
            log.error("Error connecting to NATs! {}", e.toString());
        }
    }

    public void closeConnection() {
        // Close connection
        try {
            natsConnection.close();
        }
        catch (InterruptedException e) {
            log.warn("Warning:", e);
        }
    }

    void publishMessage(String subject, String message) {
        try {
            natsConnection.publish(subject, message.getBytes());
        } catch (IllegalStateException ise) {
            log.error("Error publishing message: {} to {} ", message, subject, ise);
        }
    }

    void publishMessageWithReply(String subject, String replyTo, String message) {
        try {
            natsConnection.publish(subject, replyTo, message.getBytes());
        } catch (IllegalStateException ise) {
            log.error("Error publishing message: {} to {} ", message, subject, ise);
        }
    }

    public Subscription subscribeAsync(String subject) {
        Dispatcher dispatcher = natsConnection.createDispatcher();
        Subscription subscription = dispatcher.subscribe(subject, msg -> log.info("Subscription received message on {}", msg));
        if (subscription == null) {
            log.error("Error subscribing to {}", subject);
            return null;
        }
        dispatcherBySubscription.put(subscription, dispatcher);
        return subscription;
    }

    public Subscription subscribeAsyncInQueueGroup(String subject, String queueGroup) {
        Dispatcher dispatcher = natsConnection.createDispatcher();
        Subscription subscription = dispatcher.subscribe(subject, queueGroup, msg -> log.info("Queue group subscription received message on {}", msg));
        if (subscription == null) {
            log.error("Error subscribing to {}", subject);
            return null;
        }
        dispatcherBySubscription.put(subscription, dispatcher);
        return subscription;
    }

    public Subscription makeAsyncReplier(String subject, String reply) {
        Dispatcher dispatcher = natsConnection.createDispatcher();
        Subscription subscription = dispatcher.subscribe(subject, message -> publishMessage(message.getReplyTo(), reply));
        if (subscription == null) {
            log.error("Error making replier to {}", subject);
            return null;
        }
        dispatcherBySubscription.put(subscription, dispatcher);
        return subscription;
    }

    public Subscription subscribeSync(String subject) {
        return natsConnection.subscribe(subject);
    }

    public Subscription subscribeSyncInQueueGroup(String subject, String queueGroup) {
        return natsConnection.subscribe(subject, queueGroup);
    }

    public CompletableFuture<Message> makeRequest(String subject, String request) {
        return natsConnection.request(subject, request.getBytes());
    }

    public void unsubscribe(Subscription subscription) {
        // async subscriptions made with a dispatcher must be unsubscribed through the dispatcher
        // sync subscriptions can be unsubscribed directly through the subscription
        Dispatcher dispatcher = dispatcherBySubscription.remove(subscription);
        if (dispatcher == null) {
            subscription.unsubscribe();
        }
        else {
            dispatcher.unsubscribe(subscription);
        }
    }
}
