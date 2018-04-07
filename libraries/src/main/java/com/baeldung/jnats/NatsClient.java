package com.baeldung.jnats;

import io.nats.client.*;

import java.io.IOException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NatsClient {

    private String serverURI;

    private Connection natsConnection;

    private Map<String, Subscription> subscriptions = new HashMap<>();

    private final static Logger log = LoggerFactory.getLogger(NatsClient.class);

    public NatsClient() {
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
              .errorCb(new ExceptionHandler() {
                  @Override
                  public void onException(NATSException ex) {
                      log.error("Connection Exception: ", ex);
                  }
              })
              .disconnectedCb(new DisconnectedCallback() {
                  @Override
                  public void onDisconnect(ConnectionEvent event) {
                      log.error("Channel disconnected: {}", event.getConnection());
                  }
              })
              .reconnectedCb(new ReconnectedCallback() {
                  @Override
                  public void onReconnect(ConnectionEvent event) {
                      log.error("Reconnected to server: {}", event.getConnection());
                  }
              })
              .build();

            return Nats.connect(uri, options);

        } catch (IOException ioe) {
            log.error("Error connecting to NATs! ", ioe);
            return null;
        }
    }


    public void publishMessage(String topic, String replyTo, String message) {
        try {
            // Simple Publisher
            natsConnection.publish(topic, replyTo, message.getBytes());
        } catch (IOException ioe) {
            log.error("Error publishing message: {} to {} ", message, topic, ioe);
        }
    }


    public void subscribeAsync(String topic) {

        // Simple Async Subscriber
        AsyncSubscription subscription = natsConnection.subscribe(topic, new MessageHandler() {
            @Override
            public void onMessage(Message msg) {
                log.info("Received message on {}", msg.getSubject());
            }
        });

        if (subscription == null) {
            log.error("Error subscribing to {}", topic);
        } else {
            subscriptions.put(topic, subscription);
        }
    }

    public SyncSubscription subscribeSync(String topic) {
        // Simple Sync Subscriber
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


    public Message makeRequest(String topic, String request) {

        try {
            return natsConnection.request(topic, request.getBytes(), 100);
        } catch (IOException | InterruptedException ioe) {
            log.error("Error making request {} to {} ", topic, request, ioe);
            return null;
        }
    }

    public void installReply(String topic, String reply) {
        natsConnection.subscribe(topic, message -> {
            try {
                natsConnection.publish(message.getReplyTo(), reply.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public SyncSubscription joinQueueGroup(String topic, String queue) {
        return natsConnection.subscribe(topic, queue);
    }

}
