package com.baeldung.kafka.sharedbroker;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.shaded.org.awaitility.Awaitility;

@SpringBootTest
class PaymentListenerTest {

    private static final EmbeddedKafkaBroker broker = EmbeddedKafkaHolder.getEmbeddedKafka();

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @DynamicPropertySource
    static void kafkaProps(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", broker::getBrokersAsString);
    }

    @Test
    void givenKafkaBroker_whenPaymentMessageIsSent_thenListenerConsumesMessages() {
        kafkaTemplate.send("payment", "key", "{\"paymentId\":%s}".formatted(UUID.randomUUID()
            .toString()));
        Awaitility.await()
            .atMost(10, TimeUnit.SECONDS)
            .pollInterval(500, TimeUnit.MILLISECONDS)
            .until(() -> PaymentListener.getLatch()
                .getCount() == 0);
    }

}