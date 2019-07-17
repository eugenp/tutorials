package com.baeldung;

import java.io.IOException;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.SubscriptionType;

public class ConsumerTest {

    private static final String SERVICE_URL = "pulsar://localhost:6650";
    private static final String TOPIC_NAME = "test-topic";
    private static final String SUBSCRIPTION_NAME = "test-subscription";

    public static void main(String[] args) throws IOException {
        // Create a Pulsar client instance. A single instance can be shared across many
        // producers and consumer within the same application
        PulsarClient client = PulsarClient.builder()
                .serviceUrl(SERVICE_URL)
                .build();

        //Configure consumer specific settings.
        Consumer<byte[]> consumer = client.newConsumer()
                .topic(TOPIC_NAME)
                // Allow multiple consumers to attach to the same subscription
                // and get messages dispatched as a queue
                .subscriptionType(SubscriptionType.Shared)
                .subscriptionName(SUBSCRIPTION_NAME)
                .subscribe();


        // Once the consumer is created, it can be used for the entire application lifecycle
        System.out.println("Created consumer for the topic "+ TOPIC_NAME);

        do {
            // Wait until a message is available
            Message<byte[]> msg = consumer.receive();

            // Extract the message as a printable string and then log
            String content = new String(msg.getData());
            System.out.println("Received message '"+content+"' with ID "+msg.getMessageId());

            // Acknowledge processing of the message so that it can be deleted
            consumer.acknowledge(msg);
        } while (true);
    }
}
