package com.baeldung.dlt;

import com.baeldung.dlt.listener.PaymentListenerDltFailOnError;
import com.baeldung.dlt.listener.PaymentListenerDltRetryOnError;
import com.baeldung.dlt.listener.PaymentListenerNoDlt;
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
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static com.baeldung.dlt.PaymentTestUtils.createPayment;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = KafkaDltApplication.class,
    properties = "spring.kafka.bootstrap-servers=localhost:9095")
@EmbeddedKafka(
    partitions = 1,
    brokerProperties = { "listeners=PLAINTEXT://localhost:9095", "port=9095" },
    topics = {"payments-fail-on-error-dlt", "payments-retry-on-error-dlt", "payments-no-dlt"}
)
@ActiveProfiles("dlt")
public class KafkaDltManualTest {
    private static final String FAIL_ON_ERROR_TOPIC = "payments-fail-on-error-dlt";
    private static final String RETRY_ON_ERROR_TOPIC = "payments-retry-on-error-dlt";
    private static final String NO_DLT_TOPIC = "payments-no-dlt";

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @Autowired
    private KafkaTemplate<String, Payment> kafkaProducer;

    @SpyBean
    private PaymentListenerDltFailOnError failOnErrorPaymentsConsumer;

    @SpyBean
    private PaymentListenerDltRetryOnError retryOnErrorPaymentsConsumer;

    @SpyBean
    private PaymentListenerNoDlt noDltPaymentsConsumer;

    @BeforeEach
    void setUp() {
        // wait for embedded Kafka
        for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry.getListenerContainers()) {
            ContainerTestUtils.waitForAssignment(messageListenerContainer, 1);
        }
    }

    @Test
    public void whenMainDltFailOnErrorConsumerSucceeds_thenNoDltMessage() throws Exception {
        CountDownLatch mainTopicCountDownLatch = new CountDownLatch(1);

        doAnswer(invocation -> {
            mainTopicCountDownLatch.countDown();
            return null;
        }).when(failOnErrorPaymentsConsumer)
            .handlePayment(any(), any());

        kafkaProducer.send(FAIL_ON_ERROR_TOPIC, createPayment("dlt-fail-main"));

        assertThat(mainTopicCountDownLatch.await(5, TimeUnit.SECONDS)).isTrue();
        verify(failOnErrorPaymentsConsumer, never()).handleDltPayment(any(), any());
    }

    @Test
    public void whenDltConsumerFails_thenDltProcessingStops() throws Exception {
        CountDownLatch mainTopicCountDownLatch = new CountDownLatch(1);
        CountDownLatch dlTTopicCountDownLatch = new CountDownLatch(2);

        doAnswer(invocation -> {
            mainTopicCountDownLatch.countDown();
            throw new Exception("Simulating error in main consumer");
        }).when(failOnErrorPaymentsConsumer)
            .handlePayment(any(), any());

        doAnswer(invocation -> {
            dlTTopicCountDownLatch.countDown();
            throw new Exception("Simulating error in dlt consumer");
        }).when(failOnErrorPaymentsConsumer)
            .handleDltPayment(any(), any());

        kafkaProducer.send(FAIL_ON_ERROR_TOPIC, createPayment("dlt-fail"));

        assertThat(mainTopicCountDownLatch.await(5, TimeUnit.SECONDS)).isTrue();
        assertThat(dlTTopicCountDownLatch.await(5, TimeUnit.SECONDS)).isFalse();
        assertThat(dlTTopicCountDownLatch.getCount()).isEqualTo(1);
    }

    @Test
    public void whenMainRetryOnErrorConsumerSucceeds_thenNoDltMessage() throws Exception {
        CountDownLatch mainTopicCountDownLatch = new CountDownLatch(1);

        doAnswer(invocation -> {
            mainTopicCountDownLatch.countDown();
            return null;
        }).when(retryOnErrorPaymentsConsumer)
            .handlePayment(any(), any());

        kafkaProducer.send(RETRY_ON_ERROR_TOPIC, createPayment("dlt-retry-main"));

        assertThat(mainTopicCountDownLatch.await(5, TimeUnit.SECONDS)).isTrue();
        verify(retryOnErrorPaymentsConsumer, never()).handleDltPayment(any(), any());
    }

    @Test
    public void whenDltConsumerFails_thenDltConsumerRetriesMessage() throws Exception {
        CountDownLatch mainTopicCountDownLatch = new CountDownLatch(1);
        CountDownLatch dlTTopicCountDownLatch = new CountDownLatch(3);

        doAnswer(invocation -> {
            mainTopicCountDownLatch.countDown();
            throw new Exception("Simulating error in main consumer");
        }).when(retryOnErrorPaymentsConsumer)
            .handlePayment(any(), any());

        doAnswer(invocation -> {
            dlTTopicCountDownLatch.countDown();
            throw new Exception("Simulating error in dlt consumer");
        }).when(retryOnErrorPaymentsConsumer)
            .handleDltPayment(any(), any());

        kafkaProducer.send(RETRY_ON_ERROR_TOPIC, createPayment("dlt-retry"));

        assertThat(mainTopicCountDownLatch.await(5, TimeUnit.SECONDS)).isTrue();
        assertThat(dlTTopicCountDownLatch.await(5, TimeUnit.SECONDS)).isTrue();
        assertThat(dlTTopicCountDownLatch.getCount()).isEqualTo(0);
    }

    @Test
    public void whenMainNoDltConsumerSucceeds_thenNoDltMessage() throws Exception {
        CountDownLatch mainTopicCountDownLatch = new CountDownLatch(1);

        doAnswer(invocation -> {
            mainTopicCountDownLatch.countDown();
            return null;
        }).when(noDltPaymentsConsumer)
            .handlePayment(any(), any());

        kafkaProducer.send(NO_DLT_TOPIC, createPayment("no-dlt-main"));

        assertThat(mainTopicCountDownLatch.await(5, TimeUnit.SECONDS)).isTrue();
        verify(noDltPaymentsConsumer, never()).handleDltPayment(any(), any());
    }

    @Test
    public void whenMainConsumerFails_thenDltConsumerDoesNotReceiveMessage() throws Exception {
        CountDownLatch mainTopicCountDownLatch = new CountDownLatch(1);
        CountDownLatch dlTTopicCountDownLatch = new CountDownLatch(1);

        doAnswer(invocation -> {
            mainTopicCountDownLatch.countDown();
            throw new Exception("Simulating error in main consumer");
        }).when(noDltPaymentsConsumer)
            .handlePayment(any(), any());

        doAnswer(invocation -> {
            dlTTopicCountDownLatch.countDown();
            return null;
        }).when(noDltPaymentsConsumer)
            .handleDltPayment(any(), any());

        kafkaProducer.send(NO_DLT_TOPIC, createPayment("no-dlt"));

        assertThat(mainTopicCountDownLatch.await(5, TimeUnit.SECONDS)).isTrue();
        assertThat(dlTTopicCountDownLatch.await(5, TimeUnit.SECONDS)).isFalse();
        assertThat(dlTTopicCountDownLatch.getCount()).isEqualTo(1);
    }
}