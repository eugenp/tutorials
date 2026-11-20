package com.baeldung.mqtt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;

class HiveMqMqttClientIntegrationTest {

    private static final String PUBLIC_BROKER_HOST = "broker.hivemq.com";
    private static final int PUBLIC_BROKER_PORT = 1883;

    @Test
    void givenSubscriber_whenMessageIsPublished_thenItIsReceived() throws Exception {
        String topic = "baeldung/hivemq/test/" + UUID.randomUUID();
        String payload = "Hello from Baeldung";

        Mqtt5AsyncClient subscriber = Mqtt5Client.builder()
            .identifier("baeldung-sub-" + UUID.randomUUID())
            .serverHost(PUBLIC_BROKER_HOST)
            .serverPort(PUBLIC_BROKER_PORT)
            .buildAsync();

        subscriber.connect()
            .join();

        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<String> receivedMessage = new AtomicReference<>();

        subscriber.publishes(MqttGlobalPublishFilter.SUBSCRIBED, publish -> {
            String message = new String(publish.getPayloadAsBytes(), StandardCharsets.UTF_8);
            receivedMessage.set(message);
            latch.countDown();
        });

        subscriber.subscribeWith()
            .topicFilter(topic)
            .send()
            .join();

        Mqtt5BlockingClient publisher = Mqtt5Client.builder()
            .identifier("baeldung-pub-" + UUID.randomUUID())
            .serverHost(PUBLIC_BROKER_HOST)
            .serverPort(PUBLIC_BROKER_PORT)
            .buildBlocking();

        publisher.connect();

        publisher.publishWith()
            .topic(topic)
            .payload(payload.getBytes(StandardCharsets.UTF_8))
            .send();

        assertTrue(latch.await(2, TimeUnit.SECONDS));
        assertEquals(payload, receivedMessage.get());

        publisher.disconnect();
        subscriber.disconnect()
            .join();
    }
}