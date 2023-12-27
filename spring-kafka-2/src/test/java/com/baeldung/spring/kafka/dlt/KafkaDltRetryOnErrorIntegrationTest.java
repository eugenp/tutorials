package com.baeldung.spring.kafka.dlt;

import static com.baeldung.spring.kafka.dlt.PaymentTestUtils.createPayment;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;

import com.baeldung.spring.kafka.dlt.listener.PaymentListenerDltRetryOnError;

@SpringBootTest(classes = KafkaDltApplication.class)
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9099", "port=9099" })
public class KafkaDltRetryOnErrorIntegrationTest {
    private static final String TOPIC = "payments-retry-on-error-dlt";

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @Autowired
    private KafkaTemplate<String, Payment> kafkaProducer;

    @SpyBean
    private PaymentListenerDltRetryOnError paymentsConsumer;

    @BeforeEach
    void setUp() {
        // wait for embedded Kafka
        for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry.getListenerContainers()) {
            ContainerTestUtils.waitForAssignment(messageListenerContainer, 1);
        }
    }

    @Test
    public void whenMainConsumerSucceeds_thenNoDltMessage() throws Exception {
        CountDownLatch mainTopicCountDownLatch = new CountDownLatch(1);

        doAnswer(invocation -> {
            mainTopicCountDownLatch.countDown();
            return null;
        }).when(paymentsConsumer)
            .handlePayment(any(), any());

        kafkaProducer.send(TOPIC, createPayment("dlt-retry-main"));

        assertThat(mainTopicCountDownLatch.await(5, TimeUnit.SECONDS)).isTrue();
        verify(paymentsConsumer, never()).handleDltPayment(any(), any());
    }

    @Test
    public void whenDltConsumerFails_thenDltConsumerRetriesMessage() throws Exception {
        CountDownLatch mainTopicCountDownLatch = new CountDownLatch(1);
        CountDownLatch dlTTopicCountDownLatch = new CountDownLatch(3);

        doAnswer(invocation -> {
            mainTopicCountDownLatch.countDown();
            throw new Exception("Simulating error in main consumer");
        }).when(paymentsConsumer)
            .handlePayment(any(), any());

        doAnswer(invocation -> {
            dlTTopicCountDownLatch.countDown();
            throw new Exception("Simulating error in dlt consumer");
        }).when(paymentsConsumer)
            .handleDltPayment(any(), any());

        kafkaProducer.send(TOPIC, createPayment("dlt-retry"));

        assertThat(mainTopicCountDownLatch.await(5, TimeUnit.SECONDS)).isTrue();
        assertThat(dlTTopicCountDownLatch.await(5, TimeUnit.SECONDS)).isTrue();
        assertThat(dlTTopicCountDownLatch.getCount()).isEqualTo(0);
    }
}