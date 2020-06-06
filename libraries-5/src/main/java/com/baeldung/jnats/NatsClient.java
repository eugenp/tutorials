package com.baeldung.jnats;

import io.nats.client.AsyncSubscription;
import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.Nats;
import io.nats.client.Options;
import io.nats.client.Subscription;
import io.nats.client.SyncSubscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class NatsClient {

    private final String serverURI;

    private final Connection natsConnection;

    private final Map<String, Subscription> subscriptions = new HashMap<>();

    private final static Logger log = LoggerFactory.getLogger(NatsClient.class);

    NatsClient() {
        this.serverURI = "jnats://localhost:4222";
        natsConnection = initConnection(serverURI);
    }

    public NatsClient(String serverURI) {
        if ((serverURI != null) && (!serverURI.isEmpty())) {
            this.serverURI = serverURI;
        } else {
            this.serverURI = "jnats://localhost:4222";
        }

        natsConnection = initConnection(serverURI);
    }

    public void closeConnection() {
        // Close connection
        natsConnection.close();
    }

    private Connection initConnection(String uri) {
        try {
            Options options = new Options.Builder()
              .errorCb(ex -> log.error("Connection Exception: ", ex))
              .disconnectedCb(event -> log.error("Channel disconnected: {}", event.getConnection()))
              .reconnectedCb(event -> log.error("Reconnected to server: {}", event.getConnection()))
              .build();

            return Nats.connect(uri, options);
        } catch (IOException ioe) {
            log.error("Error connecting to NATs! ", ioe);
            return null;
        }
    }

    void publishMessage(String topic, String replyTo, String message) {
        try {
            natsConnection.publish(topic, replyTo, message.getBytes());
        } catch (IOException ioe) {
            log.error("Error publishing message: {} to {} ", message, topic, ioe);
        }
    }

    public void subscribeAsync(String topic) {

        AsyncSubscription subscription = natsConnection.subscribe(
          topic, msg -> log.info("Received message on {}", msg.getSubject()));

        if (subscription == null) {
            log.error("Error subscribing to {}", topic);
        } else {
            subscriptions.put(topic, subscription);
        }
    }

    SyncSubscription subscribeSync(String topic) {
        return natsConnection.subscribe(topic);
    }

    public void unsubscribe(String topic) {
        try {
            Subscription subscription = subscriptions.get(topic);

            if (subscription != null) {
                subscription.unsubscribe();
            } else {
                log.error("{} not found. Unable to unsubscribe.", topic);
            }
        } catch (IOException ioe) {
            log.error("Error unsubscribing from {} ", topic, ioe);
        }
    }

    Message makeRequest(String topic, String request) {
        try {
            return natsConnection.request(topic, request.getBytes(), 100);
        } catch (IOException | InterruptedException ioe) {
            log.error("Error making request {} to {} ", topic, request, ioe);
            return null;
        }
    }

    void installReply(String topic, String reply) {
        natsConnection.subscribe(topic, message -> {
            try {
                natsConnection.publish(message.getReplyTo(), reply.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    SyncSubscription joinQueueGroup(String topic, String queue) {
        return natsConnection.subscribe(topic, queue);
    }
}
