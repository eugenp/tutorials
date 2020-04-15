package com.baeldung.subscriptions;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.ConsumerBuilder;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageBuilder;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.SubscriptionType;

import java.util.stream.IntStream;

public class FailoverSubscriptionUnitTest {
    private static final String SERVICE_URL = "pulsar://localhost:6650";
    private static final String TOPIC_NAME = "failover-subscription-test-topic";
    private static final String SUBSCRIPTION_NAME = "test-subscription";
    private static final SubscriptionType SUBSCRIPTION_TYPE = SubscriptionType.Failover;
    private static final int NUM_MSGS = 10;

    public static void main(String[] args) throws PulsarClientException {
        PulsarClient client = PulsarClient.builder()
                .serviceUrl(SERVICE_URL)
                .build();

        Producer<byte[]> producer = client.newProducer()
                .topic(TOPIC_NAME)
                .create();

        ConsumerBuilder<byte[]> consumerBuilder = client.newConsumer()
                .topic(TOPIC_NAME)
                .subscriptionName(SUBSCRIPTION_NAME)
                .subscriptionType(SUBSCRIPTION_TYPE);

        Consumer<byte[]> mainConsumer = consumerBuilder
                .consumerName("consumer-a")
                .messageListener((consumer, msg) -> {
                    System.out.println("Message received by main consumer");

                    try {
                        consumer.acknowledge(msg);
                    } catch (PulsarClientException e) {
                        System.out.println(e.getMessage());
                    }
                })
                .subscribe();

        Consumer<byte[]> failoverConsumer = consumerBuilder
                .consumerName("consumer-b")
                .messageListener((consumer, msg) -> {
                    System.out.println("Message received by failover consumer");

                    try {
                        consumer.acknowledge(msg);
                    } catch (PulsarClientException e) {
                        System.out.println(e.getMessage());
                    }
                })
                .subscribe();

        IntStream.range(0, NUM_MSGS).forEach(i -> {
            Message<byte[]> msg = MessageBuilder.create()
                    .setContent(String.format("message-%d", i).getBytes())
                    .build();
            try {
                producer.send(msg);

                Thread.sleep(100);

                if (i > 5) mainConsumer.close();
            } catch (InterruptedException | PulsarClientException e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
