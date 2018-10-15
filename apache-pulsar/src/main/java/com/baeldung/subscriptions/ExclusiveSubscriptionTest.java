package com.baeldung.subscriptions;

import org.apache.pulsar.client.api.ConsumerBuilder;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageBuilder;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.SubscriptionType;

import java.util.stream.IntStream;

public class ExclusiveSubscriptionTest {
    private static final String SERVICE_URL = "pulsar://localhost:6650";
    private static final String TOPIC_NAME = "test-topic";
    private static final String SUBSCRIPTION_NAME = "test-subscription";
    private static final SubscriptionType SUBSCRIPTION_TYPE = SubscriptionType.Exclusive;

    public static void main(String[] args) throws PulsarClientException {
        PulsarClient client = PulsarClient.builder()
                .serviceUrl(SERVICE_URL)
                .build();

        Producer<byte[]> producer = client.newProducer()
                .topic(TOPIC_NAME)
                .create();

        ConsumerBuilder<byte[]> consumer1 = client.newConsumer()
                .topic(TOPIC_NAME)
                .subscriptionName(SUBSCRIPTION_NAME)
                .subscriptionType(SUBSCRIPTION_TYPE);

        ConsumerBuilder<byte[]> consumer2 = client.newConsumer()
                .topic(TOPIC_NAME)
                .subscriptionName(SUBSCRIPTION_NAME)
                .subscriptionType(SUBSCRIPTION_TYPE);

        IntStream.range(0, 999).forEach(i -> {
            Message<byte[]> msg = MessageBuilder.create()
                    .setContent(String.format("message-%d", i).getBytes())
                    .build();
            try {
                producer.send(msg);
            } catch (PulsarClientException e) {
                System.out.println(e.getMessage());
            }
        });

        // Consumer 1 can subscribe to the topic
        consumer1.subscribe();

        // Consumer 2 cannot due to the exclusive subscription held by consumer 1
        consumer2.subscribeAsync()
                .handle((consumer, exception) -> {
                    System.out.println(exception.getMessage());
                    return null;
                });
    }
}
