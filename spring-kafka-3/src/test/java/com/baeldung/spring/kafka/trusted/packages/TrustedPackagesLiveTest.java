package com.baeldung.spring.kafka.trusted.packages;

import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * This test requires a running instance of kafka to be present
 */
@SpringBootTest(classes = TrustedPackageApp.class)
@Disabled("This test requires a running instance of kafka to be present - manually run it")
public class TrustedPackagesLiveTest {

    @Autowired
    private KafkaTemplate<Object, SomeData> kafkaTemplate;

    @SpyBean
    TestConsumer testConsumer;

    @Test
    void givenMessageInTheTopic_whenTypeInfoPackageIsTrusted_thenMessageIsSuccessfullyConsumed() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Mockito.doAnswer(invocationOnMock -> {
            try {
                latch.countDown();
                return invocationOnMock.callRealMethod();
            } catch (Exception e) {
                return null;
            }
        }).when(testConsumer).onMessage(Mockito.any());

        SomeData someData = new SomeData("1", "active", "sent", Instant.now());
        kafkaTemplate.send(new ProducerRecord<>("sourceTopic", null, someData));

        Assertions.assertTrue(latch.await(10, TimeUnit.SECONDS));
    }

    @Component
    static class TestConsumer {

        @KafkaListener(topics = "sourceTopic", containerFactory = "messageListenerContainer")
        public void onMessage(SomeData someData) {

        }
    }
}
